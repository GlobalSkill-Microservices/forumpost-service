package com.globalskills.forum_service.Forum.Service;

import com.globalskills.forum_service.Forum.Entity.Comment;
import com.globalskills.forum_service.Forum.Exception.CommentException;
import com.globalskills.forum_service.Forum.Repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommentQueryService {

    @Autowired
    CommentRepo commentRepo;

    public Comment findCommentById(Long id){
        return commentRepo.findCommentById(id).orElseThrow(()-> new CommentException("Can't find comment", HttpStatus.NOT_FOUND));
    }
}
