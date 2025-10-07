package com.globalskills.forum_service.Forum.Service;

import com.globalskills.forum_service.Forum.Dto.ForumPostRequest;
import com.globalskills.forum_service.Forum.Dto.ForumPostResponse;
import com.globalskills.forum_service.Forum.Entity.ForumPost;
import com.globalskills.forum_service.Forum.Repository.ForumPostRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ForumPostCommandService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ForumPostRepo forumPostRepo;

    @Autowired
    ForumPostQueryService forumPostQueryService;

    public ForumPostResponse create (ForumPostRequest request,Long accountId){
        ForumPost forumPost = modelMapper.map(request,ForumPost.class);
        forumPost.setAccountId(accountId);
        forumPost.setCreatedAt(new Date());
        forumPost.setIsPublic(request.getIsPublic());
        forumPostRepo.save(forumPost);
        return modelMapper.map(forumPost, ForumPostResponse.class);
    }

    public ForumPostResponse update (ForumPostRequest request,Long id){
        ForumPost oldPost = forumPostQueryService.findForumPostById(id);
        oldPost.setIsPublic(request.getIsPublic());
        oldPost.setTitle(request.getTitle());
        oldPost.setContent(request.getContent());
        oldPost.setUpdatedAt(new Date());
        forumPostRepo.save(oldPost);
        return modelMapper.map(oldPost, ForumPostResponse.class);
    }

    public void delete (Long id){
        ForumPost forumPost = forumPostQueryService.findForumPostById(id);
        forumPost.setIsDeleted(true);
        forumPostRepo.save(forumPost);
    }
}
