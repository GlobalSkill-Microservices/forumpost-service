package com.globalskills.forum_service.Forum.Service.Forum;

import com.globalskills.forum_service.Common.AccountDto;
import com.globalskills.forum_service.Common.PageResponse;
import com.globalskills.forum_service.Forum.Dto.ForumPostResponse;
import com.globalskills.forum_service.Forum.Dto.SharePostResponse;
import com.globalskills.forum_service.Forum.Entity.ForumPost;
import com.globalskills.forum_service.Forum.Exception.ForumException;
import com.globalskills.forum_service.Forum.Repository.ForumPostRepo;
import com.globalskills.forum_service.Forum.Service.ServiceClient.AccountClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ForumPostQueryService {

    @Autowired
    ForumPostRepo forumPostRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AccountClientService accountClientService;

    public ForumPost findForumPostById(Long id){
        return forumPostRepo.findForumPostById(id).orElseThrow(()->new ForumException("Can't find forum post", HttpStatus.NOT_FOUND));
    }

    public ForumPostResponse getForumPostById(Long id){
        ForumPost forumPost = findForumPostById(id);
        AccountDto accountDto = accountClientService.fetchAccount(forumPost.getAccountId());
        ForumPostResponse forumPostResponse = modelMapper.map(forumPost, ForumPostResponse.class);
        forumPostResponse.setAccountId(accountDto);
        return forumPostResponse;
    }

    @Transactional(readOnly = true)
    public PageResponse<ForumPostResponse> getListForumByAccountId(
            int page, int size, String sortBy, String sortDir, Long accountId) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<ForumPost> forumPostPage = forumPostRepo.findAllByAccountId(pageRequest, accountId);
        if (forumPostPage.isEmpty()) {
            return new PageResponse<>(Collections.emptyList(), page, size, 0, 0, true);
        }

        List<ForumPostResponse> responses = mapForumPostsWithAccountInfo(forumPostPage.getContent());
        return new PageResponse<>(responses, page, size,
                forumPostPage.getTotalElements(),
                forumPostPage.getTotalPages(),
                forumPostPage.isLast());
    }


    @Transactional(readOnly = true)
    public PageResponse<ForumPostResponse> getAllListForum(
            int page, int size, String sortBy, String sortDir) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<ForumPost> forumPostPage = forumPostRepo.findAll(pageRequest);
        if (forumPostPage.isEmpty()) {
            return new PageResponse<>(Collections.emptyList(), page, size, 0, 0, true);
        }

        List<ForumPostResponse> responses = mapForumPostsWithAccountInfo(forumPostPage.getContent());
        return new PageResponse<>(responses, page, size,
                forumPostPage.getTotalElements(),
                forumPostPage.getTotalPages(),
                forumPostPage.isLast());
    }


    @Transactional(readOnly = true)
    public PageResponse<ForumPostResponse> getListShareOfPost(
            Long forumPostId, int page, int size, String sortBy, String sortDir) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<ForumPost> sharedPostsPage = forumPostRepo.findAllBySharedPost_Id(pageRequest, forumPostId);
        if (sharedPostsPage.isEmpty()) {
            return new PageResponse<>(Collections.emptyList(), page, size, 0, 0, true);
        }

        List<ForumPostResponse> responses = mapForumPostsWithAccountInfo(sharedPostsPage.getContent());
        return new PageResponse<>(responses, page, size,
                sharedPostsPage.getTotalElements(),
                sharedPostsPage.getTotalPages(),
                sharedPostsPage.isLast());
    }


    private List<ForumPostResponse> mapForumPostsWithAccountInfo(List<ForumPost> forumPosts) {
        if (forumPosts.isEmpty()) return Collections.emptyList();

        // Gom toàn bộ accountId từ post + sharedPost (nếu có)
        Set<Long> allAccountIds = new HashSet<>();
        for (ForumPost post : forumPosts) {
            if (post.getAccountId() != null) allAccountIds.add(post.getAccountId());
            if (post.getSharedPost() != null && post.getSharedPost().getAccountId() != null) {
                allAccountIds.add(post.getSharedPost().getAccountId());
            }
        }

        List<AccountDto> accounts = accountClientService.fetchListAccount(new ArrayList<>(allAccountIds));
        Map<Long, AccountDto> accountMap = accounts.stream()
                .collect(Collectors.toMap(AccountDto::getId, a -> a));

        // Map sang response
        return forumPosts.stream().map(post -> {
            ForumPostResponse response = modelMapper.map(post, ForumPostResponse.class);

            response.setAccountId(accountMap.get(post.getAccountId()));

            if (post.getSharedPost() != null) {
                SharePostResponse shareDto = modelMapper.map(post.getSharedPost(), SharePostResponse.class);
                shareDto.setAccountId(accountMap.get(post.getSharedPost().getAccountId()));
                response.setSharePostResponse(shareDto);
            }

            return response;
        }).toList();
    }

}
