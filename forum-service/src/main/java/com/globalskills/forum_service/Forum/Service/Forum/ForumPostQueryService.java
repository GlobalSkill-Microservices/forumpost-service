package com.globalskills.forum_service.Forum.Service.Forum;

import com.globalskills.forum_service.Common.PageResponse;
import com.globalskills.forum_service.Forum.Dto.ForumPostResponse;
import com.globalskills.forum_service.Forum.Dto.SharePostResponse;
import com.globalskills.forum_service.Forum.Entity.ForumPost;
import com.globalskills.forum_service.Forum.Exception.ForumException;
import com.globalskills.forum_service.Forum.Repository.ForumPostRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumPostQueryService {

    @Autowired
    ForumPostRepo forumPostRepo;

    @Autowired
    ModelMapper modelMapper;

    public ForumPost findForumPostById(Long id){
        return forumPostRepo.findForumPostById(id).orElseThrow(()->new ForumException("Can't find forum post", HttpStatus.NOT_FOUND));
    }

    public ForumPostResponse getForumPostById(Long id){
        ForumPost forumPost = findForumPostById(id);
        return modelMapper.map(forumPost, ForumPostResponse.class);
    }

    public PageResponse<ForumPostResponse> getListForumByAccountId(
            int page,
            int size,
            String sortBy,
            String sortDir,
            Long accountId
    ){
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<ForumPost> forumPostPage = forumPostRepo.findAllByAccountId(pageRequest,accountId);
        if (forumPostPage.isEmpty()){
            return null;
        }
        List<ForumPostResponse> responses = forumPostPage
                .stream()
                .map(post -> {
                    ForumPostResponse response = modelMapper.map(post, ForumPostResponse.class);
                    if (post.getSharedPost() != null) {
                        SharePostResponse shareDto = modelMapper.map(post.getSharedPost(), SharePostResponse.class);
                        response.setSharePostResponse(shareDto);
                    }
                    return response;
                })
                .toList();
        return new PageResponse<>(
                responses,
                page,
                size,
                forumPostPage.getTotalElements(),
                forumPostPage.getTotalPages(),
                forumPostPage.isLast()
        );
    }

    public PageResponse<ForumPostResponse> getAllListForum(
            int page,
            int size,
            String sortBy,
            String sortDir
    ){
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<ForumPost> forumPostPage = forumPostRepo.findAll(pageRequest);
        if(forumPostPage.isEmpty()){
            return null;
        }
        List<ForumPostResponse> responses = forumPostPage
                .stream()
                .map(post -> {
                    ForumPostResponse response = modelMapper.map(post, ForumPostResponse.class);
                    if (post.getSharedPost() != null) {
                        SharePostResponse shareDto = modelMapper.map(post.getSharedPost(), SharePostResponse.class);
                        response.setSharePostResponse(shareDto);
                    }
                    return response;
                })
                .toList();
        return new PageResponse<>(
                responses,
                page,
                size,
                forumPostPage.getTotalElements(),
                forumPostPage.getTotalPages(),
                forumPostPage.isLast()
        );
    }

    public PageResponse<ForumPostResponse> getListShareOfPost(
            Long forumPostId,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<ForumPost> sharedPostsPage = forumPostRepo.findAllBySharedPost_Id(pageRequest, forumPostId);

        if (sharedPostsPage.isEmpty()) {
            return null;
        }

        List<ForumPostResponse> responses = sharedPostsPage
                .stream()
                .map(post -> {
                    ForumPostResponse response = modelMapper.map(post, ForumPostResponse.class);
                    if (post.getSharedPost() != null) {
                        SharePostResponse shareDto = modelMapper.map(post.getSharedPost(), SharePostResponse.class);
                        response.setSharePostResponse(shareDto);
                    }
                    return response;
                })
                .toList();

        return new PageResponse<>(
                responses,
                page,
                size,
                sharedPostsPage.getTotalElements(),
                sharedPostsPage.getTotalPages(),
                sharedPostsPage.isLast()
        );
    }
}
