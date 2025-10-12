package com.globalskills.forum_service.Forum.Service.Comment;

import com.globalskills.forum_service.Forum.Dto.CommentRequest;
import com.globalskills.forum_service.Forum.Dto.CommentResponse;
import com.globalskills.forum_service.Forum.Dto.ParentCommentResponse;
import com.globalskills.forum_service.Forum.Entity.Comment;
import com.globalskills.forum_service.Forum.Entity.ForumPost;
import com.globalskills.forum_service.Forum.Repository.CommentRepo;
import com.globalskills.forum_service.Forum.Repository.ForumPostRepo;
import com.globalskills.forum_service.Forum.Service.Forum.ForumPostQueryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentCommandService {

    @Autowired
    CommentQueryService commentQueryService;

    @Autowired
    ForumPostQueryService forumPostQueryService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    ForumPostRepo forumPostRepo;

    public CommentResponse create (CommentRequest request,Long replyCommentId,Long accountId){
        ForumPost forumPost = forumPostQueryService.findForumPostById(request.getPostId());
        if(replyCommentId!=null){
            Comment parentComment = commentQueryService.findCommentById(replyCommentId);
            Comment newComment = modelMapper.map(request,Comment.class);
            newComment.setAccountId(accountId);
            newComment.setCreatedAt(new Date());
            newComment.setPost(forumPost);
            newComment.setParent(parentComment);
            commentRepo.save(newComment);

            forumPost.setCommentCount(forumPost.getCommentCount()+1);
            forumPostRepo.save(forumPost);

            CommentResponse commentResponse = modelMapper.map(newComment, CommentResponse.class);
            ParentCommentResponse parentCommentResponse = modelMapper.map(parentComment, ParentCommentResponse.class);
            commentResponse.setParentCommentResponse(parentCommentResponse);
            return commentResponse;
        }
        Comment newComment = modelMapper.map(request,Comment.class);
        newComment.setAccountId(accountId);
        newComment.setCreatedAt(new Date());
        newComment.setPost(forumPost);

        forumPost.setCommentCount(forumPost.getCommentCount()+1);
        forumPostRepo.save(forumPost);

        commentRepo.save(newComment);
        return modelMapper.map(newComment, CommentResponse.class);
    }

    public CommentResponse update (CommentRequest request,Long id){
        Comment oldComment = commentQueryService.findCommentById(id);
        oldComment.setContent(request.getContent());
        oldComment.setUpdatedAt(new Date());
        commentRepo.save(oldComment);
        if(oldComment.getParent()!=null){
            CommentResponse commentResponse = modelMapper.map(oldComment, CommentResponse.class);
            ParentCommentResponse parentCommentResponse = modelMapper.map(oldComment.getParent(), ParentCommentResponse.class);
            commentResponse.setParentCommentResponse(parentCommentResponse);
            return commentResponse;
        }
        return modelMapper.map(oldComment, CommentResponse.class);
    }

    public void delete (Long id){

    }
}
