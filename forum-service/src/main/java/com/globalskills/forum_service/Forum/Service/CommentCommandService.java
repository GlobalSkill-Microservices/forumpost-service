package com.globalskills.forum_service.Forum.Service;

import com.globalskills.forum_service.Forum.Dto.CommentRequest;
import com.globalskills.forum_service.Forum.Dto.CommentResponse;
import com.globalskills.forum_service.Forum.Entity.Comment;
import com.globalskills.forum_service.Forum.Entity.ForumPost;
import com.globalskills.forum_service.Forum.Repository.CommentRepo;
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

    public CommentResponse create (CommentRequest request,Long accountId){
        Comment comment = modelMapper.map(request,Comment.class);
        comment.setAccountId(accountId);
        comment.setCreatedAt(new Date());

        ForumPost forumPost = forumPostQueryService.findForumPostById(request.getPostId());
        comment.setPost(forumPost);
        commentRepo.save(comment);
        return modelMapper.map(comment, CommentResponse.class);
    }

    public CommentResponse update (CommentRequest request,Long id){
        Comment oldComment = commentQueryService.findCommentById(id);
        oldComment.setContent(request.getContent());
        oldComment.setUpdatedAt(new Date());
        commentRepo.save(oldComment);
        return modelMapper.map(oldComment, CommentResponse.class);
    }

    public void delete (Long id){

    }
}
