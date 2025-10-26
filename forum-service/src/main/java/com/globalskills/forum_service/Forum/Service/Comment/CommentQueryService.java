package com.globalskills.forum_service.Forum.Service.Comment;

import com.globalskills.forum_service.Common.AccountDto;
import com.globalskills.forum_service.Common.PageResponse;
import com.globalskills.forum_service.Forum.Dto.CommentResponse;
import com.globalskills.forum_service.Forum.Dto.ParentCommentResponse;
import com.globalskills.forum_service.Forum.Dto.ReplyCommentResponse;
import com.globalskills.forum_service.Forum.Entity.Comment;
import com.globalskills.forum_service.Forum.Exception.CommentException;
import com.globalskills.forum_service.Forum.Repository.CommentRepo;
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
public class CommentQueryService {

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AccountClientService accountClientService;

    public Comment findCommentById(Long id){
        return commentRepo.findCommentById(id).orElseThrow(()-> new CommentException("Can't find comment", HttpStatus.NOT_FOUND));
    }

    public CommentResponse getCommentById(Long id){
        Comment comment = findCommentById(id);
        AccountDto accountDto = accountClientService.fetchAccount(comment.getAccountId());
        CommentResponse response = modelMapper.map(comment, CommentResponse.class);
        response.setAccountId(accountDto);
        return response;
    }

    @Transactional(readOnly = true)
    public PageResponse<ParentCommentResponse> getListCommentsByPostId(
            int page,
            int size,
            String sortBy,
            String sortDir,
            Long forumPostId
    ){
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<Comment> parentComments = commentRepo.findAllByPost_IdAndParentIsNull(pageRequest, forumPostId);
        if (parentComments.isEmpty()) {
            return new PageResponse<>(
                    Collections.emptyList(),
                    page,
                    size,
                    0,
                    0,
                    true
            );
        }
        List<Long> parentIds = parentComments.getContent().stream()
                .map(Comment::getId)
                .toList();
        List<Comment> allReplies = commentRepo.findAllByParent_IdIn(parentIds);

        Set<Long> allAccountIds = new HashSet<>();
        parentComments.forEach(pc -> allAccountIds.add(pc.getAccountId()));
        allReplies.forEach(rc -> allAccountIds.add(rc.getAccountId()));

        List<AccountDto> accounts = accountClientService.fetchListAccount(new ArrayList<>(allAccountIds));

        Map<Long, AccountDto> accountMap = accounts.stream()
                .collect(Collectors.toMap(AccountDto::getId, a -> a));

        Map<Long, List<Comment>> repliesByParentId = allReplies.stream()
                .collect(Collectors.groupingBy(c -> c.getParent().getId()));

        List<ParentCommentResponse> parentCommentResponses = parentComments
                .getContent()
                .stream()
                .map(parent ->{
                    ParentCommentResponse parentDto = modelMapper.map(parent, ParentCommentResponse.class);
                    parentDto.setAccountId(accountMap.getOrDefault(parent.getAccountId(), null));
                    List<ReplyCommentResponse> replyCommentResponses = Optional.ofNullable(repliesByParentId.get(parent.getId()))
                            .orElse(Collections.emptyList())
                            .stream()
                            .map(reply -> {
                                ReplyCommentResponse replyDto = modelMapper.map(reply, ReplyCommentResponse.class);
                                replyDto.setAccountId(accountMap.getOrDefault(reply.getAccountId(), null));
                                replyDto.setParentId(parent.getId());
                                return replyDto;

                            })
                            .toList();
                    parentDto.setReplyCommentResponses(replyCommentResponses);
                    return parentDto;
                })
                .toList();
        return new PageResponse<>(
                parentCommentResponses,
                page,
                size,
                parentComments.getTotalElements(),
                parentComments.getTotalPages(),
                parentComments.isLast()
        );
    }

    @Transactional(readOnly = true)
    public PageResponse<ReplyCommentResponse> getListRepliesByParentCommentId(
            Long parentCommentId,
            int page,
            int size,
            String sortBy,
            String sortDir
    ){
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<Comment> commentPage = commentRepo.findAllByParent_Id(pageRequest,parentCommentId);
        if(commentPage.isEmpty()){
            return new PageResponse<>(
                    Collections.emptyList(),
                    page,
                    size,
                    0,
                    0,
                    true
            );
        }

        Map<Long, AccountDto> accountMap = fetchAccountMapByComments(commentPage.getContent());

        List<ReplyCommentResponse> replyCommentResponses = commentPage
                .stream()
                .map(reply -> {
                    ReplyCommentResponse response = modelMapper.map(reply, ReplyCommentResponse.class);
                    response.setAccountId(accountMap.getOrDefault(reply.getAccountId(), null));
                    return response;
                })
                .toList();
        return new PageResponse<>(
                replyCommentResponses,
                page,
                size,
                commentPage.getTotalElements(),
                commentPage.getTotalPages(),
                commentPage.isLast()
        );
    }

    @Transactional(readOnly = true)
    public PageResponse<ReplyCommentResponse> getListParentCommentsByPostId(
            Long forumPostId,
            int page,
            int size,
            String sortBy,
            String sortDir
    ){
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<Comment> commentPage = commentRepo.findAllByPost_IdAndParentIsNull(pageRequest,forumPostId);
        if(commentPage.isEmpty()){
            return new PageResponse<>(
                    Collections.emptyList(),
                    page,
                    size,
                    0,
                    0,
                    true
            );
        }

        Map<Long, AccountDto> accountMap = fetchAccountMapByComments(commentPage.getContent());

        List<ReplyCommentResponse> replyCommentResponses = commentPage
                .stream()
                .map(reply -> {
                    ReplyCommentResponse response = modelMapper.map(reply, ReplyCommentResponse.class);
                    response.setAccountId(accountMap.getOrDefault(reply.getAccountId(), null));
                    return response;
                })
                .toList();
        return new PageResponse<>(
                replyCommentResponses,
                page,
                size,
                commentPage.getTotalElements(),
                commentPage.getTotalPages(),
                commentPage.isLast()
        );
    }

    private Map<Long, AccountDto> fetchAccountMapByComments(Collection<Comment> comments) {
        if (comments == null || comments.isEmpty()) {
            return Collections.emptyMap();
        }
        Set<Long> accountIds = comments.stream()
                .map(Comment::getAccountId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (accountIds.isEmpty()) {
            return Collections.emptyMap();
        }

        List<AccountDto> accounts = accountClientService.fetchListAccount(new ArrayList<>(accountIds));
        return accounts.stream()
                .collect(Collectors.toMap(AccountDto::getId, a -> a));
    }


}
