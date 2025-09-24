package com.globalskills.forum_service.Forum.Service;

import com.globalskills.forum_service.Forum.Entity.Share;
import com.globalskills.forum_service.Forum.Exception.ShareException;
import com.globalskills.forum_service.Forum.Repository.ShareRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ShareQueryService {

    @Autowired
    ShareRepo shareRepo;


    public Share findShareById(Long id){
        return shareRepo.findShareById(id).orElseThrow(()-> new ShareException("Can't find share", HttpStatus.NOT_FOUND));
    }
}
