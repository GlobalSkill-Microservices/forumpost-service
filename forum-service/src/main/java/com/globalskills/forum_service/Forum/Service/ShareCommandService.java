package com.globalskills.forum_service.Forum.Service;

import com.globalskills.forum_service.Forum.Dto.ShareResponse;
import com.globalskills.forum_service.Forum.Entity.ForumPost;
import com.globalskills.forum_service.Forum.Entity.Share;
import com.globalskills.forum_service.Forum.Repository.ShareRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ShareCommandService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ShareQueryService shareQueryService;

    @Autowired
    ForumPostQueryService forumPostQueryService;

    @Autowired
    ShareRepo shareRepo;

    public ShareResponse create(Long postId,Long accountId){
        Share share = new Share();
        ForumPost forumPost = forumPostQueryService.findForumPostById(postId);
        share.setCreatedAt(new Date());
        share.setPost(forumPost);
        share.setAccountId(accountId);
        shareRepo.save(share);
        return modelMapper.map(share, ShareResponse.class);
    }
}
