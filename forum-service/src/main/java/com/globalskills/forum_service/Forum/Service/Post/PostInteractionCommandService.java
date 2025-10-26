package com.globalskills.forum_service.Forum.Service.Post;

import com.globalskills.forum_service.Common.AccountDto;
import com.globalskills.forum_service.Forum.Dto.PostInteractionRequest;
import com.globalskills.forum_service.Forum.Dto.PostInteractionResponse;
import com.globalskills.forum_service.Forum.Entity.ForumPost;
import com.globalskills.forum_service.Forum.Entity.PostInteraction;
import com.globalskills.forum_service.Forum.Exception.PostInteractionException;
import com.globalskills.forum_service.Forum.Repository.ForumPostRepo;
import com.globalskills.forum_service.Forum.Repository.PostInteractionRepo;
import com.globalskills.forum_service.Forum.Service.Forum.ForumPostQueryService;
import com.globalskills.forum_service.Forum.Service.ServiceClient.AccountClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostInteractionCommandService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PostInteractionRepo postInteractionRepo;

    @Autowired
    ForumPostRepo forumPostRepo;

    @Autowired
    ForumPostQueryService forumPostQueryService;

    @Autowired
    PostInteractionQueryService postInteractionQueryService;

    @Autowired
    AccountClientService accountClientService;

    public PostInteractionResponse create (PostInteractionRequest request, Long forumPostId,Long accountId){
        AccountDto author = accountClientService.fetchAccount(accountId);
        ForumPost forumPost = forumPostQueryService.findForumPostById(forumPostId);
        PostInteraction oldPostInteraction = postInteractionQueryService.findPostInteractionByPost_IdAndAccountId(forumPostId,accountId);
        if(oldPostInteraction!=null){
            throw new PostInteractionException("Already react", HttpStatus.BAD_REQUEST);
        }
        PostInteraction postInteraction = modelMapper.map(request,PostInteraction.class);
        postInteraction.setPost(forumPost);
        postInteraction.setAccountId(accountId);
        postInteractionRepo.save(postInteraction);
        forumPost.setInteractionCount(forumPost.getInteractionCount() + 1);
        forumPostRepo.save(forumPost);
        PostInteractionResponse postInteractionResponse = modelMapper.map(postInteraction,PostInteractionResponse.class);
        postInteractionResponse.setAccountId(author);
        return postInteractionResponse;
    }

    public PostInteractionResponse update (PostInteractionRequest request,Long id){
        PostInteraction oldInteraction = postInteractionQueryService.findPostInteractionById(id);
        AccountDto author = accountClientService.fetchAccount(oldInteraction.getAccountId());
        oldInteraction.setTypeInteraction(request.getTypeInteraction());
        postInteractionRepo.save(oldInteraction);
        PostInteractionResponse postInteractionResponse = modelMapper.map(oldInteraction,PostInteractionResponse.class);
        postInteractionResponse.setAccountId(author);
        return postInteractionResponse;

    }

    @Transactional
    public void delete (Long id){
        PostInteraction postInteraction = postInteractionQueryService.findPostInteractionById(id);
        ForumPost forumPost = forumPostQueryService.findForumPostById(postInteraction.getPost().getId());
        int newCount = Math.max(0, forumPost.getInteractionCount() - 1);
        forumPost.setInteractionCount(newCount);
        forumPostRepo.save(forumPost);
        postInteractionRepo.delete(postInteraction);
    }

}
