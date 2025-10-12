package com.globalskills.forum_service.Forum.Service.Post;

import com.globalskills.forum_service.Common.PageResponse;
import com.globalskills.forum_service.Forum.Dto.PostInteractionResponse;
import com.globalskills.forum_service.Forum.Entity.PostInteraction;
import com.globalskills.forum_service.Forum.Exception.PostInteractionException;
import com.globalskills.forum_service.Forum.Repository.PostInteractionRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostInteractionQueryService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PostInteractionRepo postInteractionRepo;

    public PostInteraction findPostInteractionByPost_IdAndAccountId(Long postId,Long accountId){
        return postInteractionRepo.findByPost_IdAndAccountId(postId,accountId);
    }

    public Boolean isReact(Long postId,Long accountId){
        PostInteraction postInteraction = findPostInteractionByPost_IdAndAccountId(postId, accountId);
        if(postInteraction==null){
            return false;
        }
        return true;
    }

    public PostInteraction findPostInteractionById(Long id){
        return postInteractionRepo.findPostInteractionById(id).orElseThrow(()->new PostInteractionException("Can't find post interaction", HttpStatus.NOT_FOUND));
    }

    public PostInteractionResponse getPostInteractionById(Long id){
        PostInteraction postInteraction = findPostInteractionById(id);
        return modelMapper.map(postInteraction, PostInteractionResponse.class);
    }

    public PageResponse<PostInteractionResponse> getListPostInteractionByForumPostId(
            Long forumPostId,
            int page,
            int size,
            String sortBy,
            String sortDir
    ){
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<PostInteraction> postInteractionPage = postInteractionRepo.findAllByPost_Id(pageRequest,forumPostId);
        if(postInteractionPage.isEmpty()){
            return null;
        }
        List<PostInteractionResponse> responses = postInteractionPage
                .stream()
                .map(postInteraction -> modelMapper.map(postInteraction, PostInteractionResponse.class))
                .toList();
        return new PageResponse<>(
                responses,
                page,
                size,
                postInteractionPage.getTotalElements(),
                postInteractionPage.getTotalPages(),
                postInteractionPage.isLast()
        );
    }
}
