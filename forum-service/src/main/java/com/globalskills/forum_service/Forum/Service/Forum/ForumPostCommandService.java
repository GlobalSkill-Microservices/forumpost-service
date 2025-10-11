package com.globalskills.forum_service.Forum.Service.Forum;

import com.globalskills.forum_service.Forum.Dto.ForumPostRequest;
import com.globalskills.forum_service.Forum.Dto.ForumPostResponse;
import com.globalskills.forum_service.Forum.Dto.SharePostResponse;
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

    public ForumPostResponse create (ForumPostRequest request,Long shareForumPostId,Long accountId){
        if(shareForumPostId!=null){
            ForumPost sharePost = forumPostQueryService.findForumPostById(shareForumPostId);
            ForumPost newPost = modelMapper.map(request,ForumPost.class);
            newPost.setAccountId(accountId);
            newPost.setSharedPost(sharePost);
            newPost.setCreatedAt(new Date());
            newPost.setShareCount(newPost.getShareCount()+1);
            forumPostRepo.save(newPost);
            ForumPostResponse forumPostResponse = modelMapper.map(newPost, ForumPostResponse.class);
            SharePostResponse sharePostResponse = modelMapper.map(sharePost,SharePostResponse.class);
            forumPostResponse.setSharePostResponse(sharePostResponse);
            return forumPostResponse;
        }
        ForumPost newPost = modelMapper.map(request,ForumPost.class);
        newPost.setAccountId(accountId);
        newPost.setCreatedAt(new Date());
        forumPostRepo.save(newPost);
        return modelMapper.map(newPost, ForumPostResponse.class);
    }

    public ForumPostResponse update (ForumPostRequest request,Long id){
        ForumPost oldPost = forumPostQueryService.findForumPostById(id);
        oldPost.setIsPublic(request.getIsPublic());
        oldPost.setTitle(request.getTitle());
        oldPost.setContent(request.getContent());
        oldPost.setUpdatedAt(new Date());
        forumPostRepo.save(oldPost);
        if(oldPost.getSharedPost()!=null){
            ForumPostResponse forumPostResponse = modelMapper.map(oldPost, ForumPostResponse.class);
            SharePostResponse sharePostResponse = modelMapper.map(oldPost.getSharedPost(), SharePostResponse.class);
            forumPostResponse.setSharePostResponse(sharePostResponse);
            return forumPostResponse;
        }
        return modelMapper.map(oldPost, ForumPostResponse.class);
    }

    public void delete (Long id){
        ForumPost forumPost = forumPostQueryService.findForumPostById(id);
        forumPost.setIsDeleted(true);
        forumPostRepo.save(forumPost);
    }
}
