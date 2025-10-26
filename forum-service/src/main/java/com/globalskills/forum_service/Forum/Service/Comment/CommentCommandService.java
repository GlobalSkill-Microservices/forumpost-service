package com.globalskills.forum_service.Forum.Service.Comment;

import com.globalskills.forum_service.Common.AccountDto;
import com.globalskills.forum_service.Forum.Dto.CommentRequest;
import com.globalskills.forum_service.Forum.Dto.CommentResponse;
import com.globalskills.forum_service.Forum.Dto.ParentCommentResponse;
import com.globalskills.forum_service.Forum.Entity.Comment;
import com.globalskills.forum_service.Forum.Entity.ForumPost;
import com.globalskills.forum_service.Forum.Repository.CommentRepo;
import com.globalskills.forum_service.Forum.Repository.ForumPostRepo;
import com.globalskills.forum_service.Forum.Service.ServiceClient.AccountClientService;
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

    @Autowired
    AccountClientService accountClientService;

    public CommentResponse create (CommentRequest request,Long postId,Long replyCommentId,Long accountId){
        ForumPost forumPost = forumPostQueryService.findForumPostById(postId);
        AccountDto reply = accountClientService.fetchAccount(accountId);
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
            commentResponse.setAccountId(reply);

            ParentCommentResponse parentCommentResponse = modelMapper.map(parentComment, ParentCommentResponse.class);
            AccountDto author = accountClientService.fetchAccount(parentComment.getAccountId());
            parentCommentResponse.setAccountId(author);
            commentResponse.setParentCommentResponse(parentCommentResponse);

            return commentResponse;
        }
        Comment newComment = modelMapper.map(request,Comment.class);
        newComment.setAccountId(accountId);
        newComment.setCreatedAt(new Date());
        newComment.setPost(forumPost);
        commentRepo.save(newComment);

        forumPost.setCommentCount(forumPost.getCommentCount()+1);
        forumPostRepo.save(forumPost);

        CommentResponse commentResponse = modelMapper.map(newComment, CommentResponse.class);
        commentResponse.setAccountId(reply);

        return commentResponse;
    }

    public CommentResponse update (CommentRequest request,Long id){
        Comment oldComment = commentQueryService.findCommentById(id);

        AccountDto reply = accountClientService.fetchAccount(oldComment.getAccountId());

        oldComment.setContent(request.getContent());
        oldComment.setUpdatedAt(new Date());
        commentRepo.save(oldComment);

        if(oldComment.getParent()!=null){
            CommentResponse commentResponse = modelMapper.map(oldComment, CommentResponse.class);
            commentResponse.setAccountId(reply);
            ParentCommentResponse parentCommentResponse = modelMapper.map(oldComment.getParent(), ParentCommentResponse.class);
            AccountDto author = accountClientService.fetchAccount(oldComment.getParent().getAccountId());
            parentCommentResponse.setAccountId(author);

            commentResponse.setParentCommentResponse(parentCommentResponse);
            return commentResponse;
        }
        CommentResponse commentResponse = modelMapper.map(oldComment, CommentResponse.class);
        commentResponse.setAccountId(reply);
        return commentResponse;
    }

    public void delete (Long id){

    }
}
