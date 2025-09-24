package com.globalskills.forum_service.Forum.Service;

import com.globalskills.forum_service.Forum.Entity.ForumPost;
import com.globalskills.forum_service.Forum.Exception.ForumException;
import com.globalskills.forum_service.Forum.Repository.ForumPostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ForumPostQueryService {

    @Autowired
    ForumPostRepo forumPostRepo;

    public ForumPost findForumPostById(Long id){
        return forumPostRepo.findForumPostById(id).orElseThrow(()->new ForumException("Can't find forum post", HttpStatus.NOT_FOUND));
    }

    public void getListMyForumPost(){

    }

    public void getListAccountForumPost(){

    }
}
