package com.globalskills.forum_service.Forum.Service.Comment;

import com.globalskills.forum_service.Common.PageResponse;
import com.globalskills.forum_service.Forum.Dto.CommentResponse;
import com.globalskills.forum_service.Forum.Dto.ParentCommentResponse;
import com.globalskills.forum_service.Forum.Entity.Comment;
import com.globalskills.forum_service.Forum.Exception.CommentException;
import com.globalskills.forum_service.Forum.Repository.CommentRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentQueryService {

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    ModelMapper modelMapper;

    public Comment findCommentById(Long id){
        return commentRepo.findCommentById(id).orElseThrow(()-> new CommentException("Can't find comment", HttpStatus.NOT_FOUND));
    }

    public CommentResponse getCommentById(Long id){
        Comment comment = findCommentById(id);
        return modelMapper.map(comment, CommentResponse.class);
    }

    public PageResponse<CommentResponse> getListCommentByPostId(
            int page,
            int size,
            String sortBy,
            String sortDir,
            Long forumPostId
    ){
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<Comment> commentPage = commentRepo.findAllByPost_Id(pageRequest,forumPostId);
        if(commentPage.isEmpty()){
            return null;
        }
        List<CommentResponse> responses = commentPage
                .stream()
                .map(comment -> {
                    CommentResponse response = modelMapper.map(comment,CommentResponse.class);
                    if(comment.getParent()!= null){
                        ParentCommentResponse parentCommentResponse = modelMapper.map(comment.getParent(), ParentCommentResponse.class);
                        response.setParentCommentResponse(parentCommentResponse);
                    }
                    return response;
                })
                .toList();
        return new PageResponse<>(
                responses,
                page,
                size,
                commentPage.getTotalElements(),
                commentPage.getTotalPages(),
                commentPage.isLast()
        );
    }

    public PageResponse<CommentResponse> getListRepliesOfComment(
            Long parentCommentId,
            int page,
            int size,
            String sortBy,
            String sortDir
    ){
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<Comment> commentPage = commentRepo.findAllByParent_Id(pageRequest,parentCommentId);
        List<CommentResponse> responses = commentPage.getContent()
                .stream()
                .map(comment -> {
                    CommentResponse response = modelMapper.map(comment, CommentResponse.class);
                    if (comment.getParent() != null) {
                        ParentCommentResponse parentDto = modelMapper.map(comment.getParent(), ParentCommentResponse.class);
                        response.setParentCommentResponse(parentDto);
                    }
                    return response;
                })
                .toList();
        return new PageResponse<>(
                responses,
                page,
                size,
                commentPage.getTotalElements(),
                commentPage.getTotalPages(),
                commentPage.isLast()
        );
    }

}
