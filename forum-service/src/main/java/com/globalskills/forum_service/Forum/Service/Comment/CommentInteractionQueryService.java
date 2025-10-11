package com.globalskills.forum_service.Forum.Service.Comment;

import com.globalskills.forum_service.Common.PageResponse;
import com.globalskills.forum_service.Forum.Dto.CommentInteractionResponse;
import com.globalskills.forum_service.Forum.Entity.CommentInteraction;
import com.globalskills.forum_service.Forum.Exception.CommentInteractionException;
import com.globalskills.forum_service.Forum.Repository.CommentInteractionRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentInteractionQueryService {

    @Autowired
    CommentInteractionRepo commentInteractionRepo;

    @Autowired
    ModelMapper modelMapper;

    public CommentInteraction findById(Long id){
        return commentInteractionRepo.findCommentInteractionById(id).orElseThrow(()-> new CommentInteractionException("Can't find comment interaction", HttpStatus.NOT_FOUND));
    }
    public CommentInteractionResponse findCommentInteractionById(Long id){
        CommentInteraction commentInteraction = findById(id);
        return modelMapper.map(commentInteraction, CommentInteractionResponse.class);
    }
    public PageResponse<CommentInteractionResponse> getListCommentInteractionByCommentId(
            int page,
            int size,
            String sortBy,
            String sortDir,
            Long commentId
    ){
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<CommentInteraction> commentInteractionPage = commentInteractionRepo.findAllByComment_Id(pageRequest,commentId);
        if(commentInteractionPage.isEmpty()){
            return null;
        }
        List<CommentInteractionResponse> responses = commentInteractionPage
                .stream()
                .map(commentInteraction -> modelMapper.map(commentInteraction, CommentInteractionResponse.class))
                .toList();
        return new PageResponse<>(
                responses,
                page,
                size,
                commentInteractionPage.getTotalElements(),
                commentInteractionPage.getTotalPages(),
                commentInteractionPage.isLast()
        );
    }
}
