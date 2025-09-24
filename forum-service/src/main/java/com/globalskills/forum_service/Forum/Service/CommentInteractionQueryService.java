package com.globalskills.forum_service.Forum.Service;

import com.globalskills.forum_service.Forum.Entity.CommentInteraction;
import com.globalskills.forum_service.Forum.Exception.CommentInteractionException;
import com.globalskills.forum_service.Forum.Repository.CommentInteractionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommentInteractionQueryService {

    @Autowired
    CommentInteractionRepo commentInteractionRepo;

    public CommentInteraction findById(Long id){
        return commentInteractionRepo.findCommentInteractionById(id).orElseThrow(()-> new CommentInteractionException("Can't find comment interaction", HttpStatus.NOT_FOUND));
    }
}
