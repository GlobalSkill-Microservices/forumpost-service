package com.globalskills.forum_service.Forum.Service.Comment;

import com.globalskills.forum_service.Forum.Dto.CommentInteractionRequest;
import com.globalskills.forum_service.Forum.Dto.CommentInteractionResponse;
import com.globalskills.forum_service.Forum.Entity.Comment;
import com.globalskills.forum_service.Forum.Entity.CommentInteraction;
import com.globalskills.forum_service.Forum.Repository.CommentInteractionRepo;
import com.globalskills.forum_service.Forum.Repository.CommentRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentInteractionCommandService {

    @Autowired
    CommentInteractionRepo commentInteractionRepo;

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CommentQueryService commentQueryService;

    @Autowired
    CommentInteractionQueryService commentInteractionQueryService;

    public CommentInteractionResponse create(CommentInteractionRequest request,Long commentId,Long accountId){
        Comment comment = commentQueryService.findCommentById(commentId);
        CommentInteraction commentInteraction = modelMapper.map(request, CommentInteraction.class);
        commentInteraction.setComment(comment);
        commentInteraction.setAccountId(accountId);
        commentInteraction.setCreatedAt(new Date());
        comment.setCommentInteractionCount(comment.getCommentInteractionCount()+1);
        commentRepo.save(comment);
        commentInteractionRepo.save(commentInteraction);
        return modelMapper.map(commentInteraction, CommentInteractionResponse.class);
    }
    public CommentInteractionResponse update(CommentInteractionRequest request,Long id){
        CommentInteraction oldCommentInteraction = commentInteractionQueryService.findById(id);
        oldCommentInteraction.setTypeInteraction(request.getTypeInteraction());
        oldCommentInteraction.setUpdatedAt(new Date());
        commentInteractionRepo.save(oldCommentInteraction);
        return modelMapper.map(oldCommentInteraction, CommentInteractionResponse.class);
    }

    public void delete(){

    }

}
