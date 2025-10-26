package com.globalskills.forum_service.Forum.Service.Forum;

import com.globalskills.forum_service.Common.AccountDto;
import com.globalskills.forum_service.Forum.Dto.ForumPostRequest;
import com.globalskills.forum_service.Forum.Dto.ForumPostResponse;
import com.globalskills.forum_service.Forum.Dto.SharePostResponse;
import com.globalskills.forum_service.Forum.Entity.ForumPost;
import com.globalskills.forum_service.Forum.Repository.ForumPostRepo;
import com.globalskills.forum_service.Forum.Service.ServiceClient.AccountClientService;
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

    @Autowired
    AccountClientService accountClientService;

    public ForumPostResponse create (ForumPostRequest request,Long shareForumPostId,Long accountId){
        AccountDto shareBy = accountClientService.fetchAccount(accountId);
        if(shareForumPostId!=null){
            ForumPost sharePost = forumPostQueryService.findForumPostById(shareForumPostId);
            AccountDto author = accountClientService.fetchAccount(sharePost.getAccountId());
            ForumPost newPost = modelMapper.map(request,ForumPost.class);
            newPost.setAccountId(accountId);
            newPost.setSharedPost(sharePost);
            newPost.setCreatedAt(new Date());
            newPost.setShareCount(newPost.getShareCount()+1);
            forumPostRepo.save(newPost);
            ForumPostResponse forumPostResponse = modelMapper.map(newPost, ForumPostResponse.class);
            forumPostResponse.setAccountId(shareBy);
            SharePostResponse sharePostResponse = modelMapper.map(sharePost,SharePostResponse.class);
            sharePostResponse.setAccountId(author);
            forumPostResponse.setSharePostResponse(sharePostResponse);
            return forumPostResponse;
        }
        ForumPost newPost = modelMapper.map(request,ForumPost.class);
        newPost.setAccountId(accountId);
        newPost.setCreatedAt(new Date());
        forumPostRepo.save(newPost);
        ForumPostResponse forumPostResponse = modelMapper.map(newPost, ForumPostResponse.class);
        forumPostResponse.setAccountId(shareBy);
        return forumPostResponse;
    }

    public ForumPostResponse update (ForumPostRequest request,Long id){
        ForumPost oldPost = forumPostQueryService.findForumPostById(id);
        oldPost.setIsPublic(request.getIsPublic());
        oldPost.setTitle(request.getTitle());
        oldPost.setContent(request.getContent());
        oldPost.setUpdatedAt(new Date());
        forumPostRepo.save(oldPost);
        AccountDto shareBy = accountClientService.fetchAccount(oldPost.getAccountId());
        if(oldPost.getSharedPost()!=null){
            ForumPostResponse forumPostResponse = modelMapper.map(oldPost, ForumPostResponse.class);
            forumPostResponse.setAccountId(shareBy);
            SharePostResponse sharePostResponse = modelMapper.map(oldPost.getSharedPost(), SharePostResponse.class);
            AccountDto author = accountClientService.fetchAccount(oldPost.getSharedPost().getAccountId());
            sharePostResponse.setAccountId(author);
            forumPostResponse.setSharePostResponse(sharePostResponse);
            return forumPostResponse;
        }
        ForumPostResponse forumPostResponse = modelMapper.map(oldPost, ForumPostResponse.class);
        forumPostResponse.setAccountId(shareBy);
        return forumPostResponse;
    }

    public void delete (Long id){
        ForumPost forumPost = forumPostQueryService.findForumPostById(id);
        forumPost.setIsDeleted(true);
        forumPostRepo.save(forumPost);
    }
}
