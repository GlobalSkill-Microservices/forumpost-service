package com.globalskills.forum_service.Forum.Controller;

import com.globalskills.forum_service.Common.BaseResponseAPI;
import com.globalskills.forum_service.Common.PageResponse;
import com.globalskills.forum_service.Forum.Dto.CommentRequest;
import com.globalskills.forum_service.Forum.Dto.CommentResponse;
import com.globalskills.forum_service.Forum.Dto.ParentCommentResponse;
import com.globalskills.forum_service.Forum.Dto.ReplyCommentResponse;
import com.globalskills.forum_service.Forum.Service.Comment.CommentCommandService;
import com.globalskills.forum_service.Forum.Service.Comment.CommentQueryService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@SecurityRequirement(name = "api")
public class CommentController {

    @Autowired
    CommentCommandService commentCommandService;

    @Autowired
    CommentQueryService commentQueryService;

    @PostMapping("/forum-post/{postId}")
    public ResponseEntity<?> create(@RequestBody CommentRequest request,
                                    @PathVariable Long postId,
                                    @RequestParam(required = false) Long replyCommentId,
                                    @Parameter(hidden = true)
                                    @RequestHeader(value = "X-User-ID",required = false) Long accountId){
        CommentResponse response = commentCommandService.create(request,postId,replyCommentId,accountId);
        BaseResponseAPI<CommentResponse> responseAPI = new BaseResponseAPI<>(true,"Create comment successfully",response,null);
        return ResponseEntity.ok(responseAPI);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody CommentRequest request,
                                    @PathVariable Long id){
        CommentResponse response = commentCommandService.update(request, id);
        BaseResponseAPI<CommentResponse> responseAPI = new BaseResponseAPI<>(true,"Update comment successfully",response,null);
        return ResponseEntity.ok(responseAPI);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable Long id){
        CommentResponse response = commentQueryService.getCommentById(id);
        BaseResponseAPI<CommentResponse> responseAPI = new BaseResponseAPI<>(true,"Get comment successfully",response,null);
        return ResponseEntity.ok(responseAPI);
    }

    @GetMapping("/all/forum-post/{forumPostId}")
    public ResponseEntity<?> getListCommentByPostId(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @PathVariable Long forumPostId
    ){
        PageResponse<ParentCommentResponse> response = commentQueryService.getListCommentsByPostId(page, size, sortBy, sortDir, forumPostId);
        BaseResponseAPI<PageResponse<ParentCommentResponse>> responseAPI = new BaseResponseAPI<>(true,"Get list comment successfully",response,null);
        return ResponseEntity.ok(responseAPI);
    }

    @GetMapping("/parent/{parentCommentId}")
    public ResponseEntity<?> getListRepliesOfComment(
            @PathVariable Long parentCommentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ){
        PageResponse<ReplyCommentResponse> response = commentQueryService.getListRepliesByParentCommentId(parentCommentId, page, size, sortBy, sortDir);
        BaseResponseAPI<PageResponse<ReplyCommentResponse>> responseAPI = new BaseResponseAPI<>(true,"Get list replies comment successfully",response,null);
        return ResponseEntity.ok(responseAPI);
    }

    @GetMapping("/parent-comment/forum-post/{forumPostId}")
    public ResponseEntity<?> getListParentCommentsByPostId(
            @PathVariable Long forumPostId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ){
        PageResponse<ReplyCommentResponse> response = commentQueryService.getListRepliesByParentCommentId(forumPostId, page, size, sortBy, sortDir);
        BaseResponseAPI<PageResponse<ReplyCommentResponse>> responseAPI = new BaseResponseAPI<>(true,"Get list parent comment successfully",response,null);
        return ResponseEntity.ok(responseAPI);

    }


}
