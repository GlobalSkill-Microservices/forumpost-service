package com.globalskills.forum_service.Forum.Service;

import com.globalskills.forum_service.Forum.Entity.PostInteraction;
import com.globalskills.forum_service.Forum.Exception.PostInteractionException;
import com.globalskills.forum_service.Forum.Repository.PostInteractionRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PostInteractionQueryService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PostInteractionRepo postInteractionRepo;

    public PostInteraction findPostInteractionById(Long id){
        return postInteractionRepo.findPostInteractionById(id).orElseThrow(()->new PostInteractionException("Can't find post interaction", HttpStatus.NOT_FOUND));
    }
}
