package com.globalskills.forum_service.Forum.Service;

import com.globalskills.forum_service.Forum.Dto.PostInteractionRequest;
import com.globalskills.forum_service.Forum.Dto.PostInteractionResponse;
import com.globalskills.forum_service.Forum.Entity.ForumPost;
import com.globalskills.forum_service.Forum.Entity.PostInteraction;
import com.globalskills.forum_service.Forum.Repository.PostInteractionRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostInteractionCommandService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PostInteractionRepo postInteractionRepo;

    @Autowired
    ForumPostQueryService forumPostQueryService;

    @Autowired
    PostInteractionQueryService postInteractionQueryService;

    public PostInteractionResponse create (PostInteractionRequest request, Long postId,Long accountId){
        ForumPost forumPost = forumPostQueryService.findForumPostById(postId);
        PostInteraction postInteraction = modelMapper.map(request,PostInteraction.class);
        postInteraction.setPost(forumPost);
        postInteraction.setAccountId(accountId);
        postInteractionRepo.save(postInteraction);
        return modelMapper.map(postInteraction,PostInteractionResponse.class);
    }

    public PostInteractionResponse update (PostInteractionRequest request,Long id){
        PostInteraction oldInteraction = postInteractionQueryService.findPostInteractionById(id);
        oldInteraction.setTypeInteraction(request.getTypeInteraction());
        postInteractionRepo.save(oldInteraction);
        return modelMapper.map(oldInteraction,PostInteractionResponse.class);

    }

    public void delete (Long id){
        PostInteraction postInteraction = postInteractionQueryService.findPostInteractionById(id);
        postInteractionRepo.delete(postInteraction);
    }

}
