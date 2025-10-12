package com.globalskills.forum_service.Forum.Controller;

import com.globalskills.forum_service.Common.BaseResponseAPI;
import com.globalskills.forum_service.Common.PageResponse;
import com.globalskills.forum_service.Forum.Dto.CommentInteractionRequest;
import com.globalskills.forum_service.Forum.Dto.CommentInteractionResponse;
import com.globalskills.forum_service.Forum.Service.Comment.CommentInteractionCommandService;
import com.globalskills.forum_service.Forum.Service.Comment.CommentInteractionQueryService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment-interaction")
@CrossOrigin("*")
@SecurityRequirement(name = "api")
public class CommentInteractionController {

    @Autowired
    CommentInteractionQueryService commentInteractionQueryService;

    @Autowired
    CommentInteractionCommandService commentInteractionCommandService;

    @GetMapping("/comment/{commentId}/is-react")
    public ResponseEntity<?> isReact(@PathVariable Long commentId,
                                     @Parameter(hidden = true)
                                     @RequestHeader(value = "X-User-ID",required = false) Long accountId){
        Boolean isReact = commentInteractionQueryService.isReact(commentId, accountId);
        BaseResponseAPI<Boolean> responseAPI = new BaseResponseAPI<>(true,"Check react",isReact,null);
        return ResponseEntity.ok(responseAPI);
    }

    @PostMapping("/comment/{commentId}")
    public ResponseEntity<?> create (@RequestBody CommentInteractionRequest request,
                                     @PathVariable Long commentId,
                                     @Parameter(hidden = true)
                                     @RequestHeader(value = "X-User-ID",required = false) Long accountId){
        CommentInteractionResponse response = commentInteractionCommandService.create(request, commentId, accountId);
        BaseResponseAPI<CommentInteractionResponse> responseAPI = new BaseResponseAPI<>(true,"Create comment interaction successfully",response,null);
        return ResponseEntity.ok(responseAPI);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody CommentInteractionRequest request,
                                    @PathVariable Long id){
        CommentInteractionResponse response = commentInteractionCommandService.update(request, id);
        BaseResponseAPI<CommentInteractionResponse> responseAPI = new BaseResponseAPI<>(true,"Update comment interaction response",response,null);
        return ResponseEntity.ok(responseAPI);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        commentInteractionCommandService.delete(id);
        BaseResponseAPI<?> responseAPI = new BaseResponseAPI<>(true,"Delete comment react successfully",null,null);
        return ResponseEntity.ok(responseAPI);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCommentInteractionById(@PathVariable Long id){
        CommentInteractionResponse response = commentInteractionQueryService.findCommentInteractionById(id);
        BaseResponseAPI<CommentInteractionResponse> responseAPI = new BaseResponseAPI<>(true,"Get comment interaction successfully",response,null);
        return ResponseEntity.ok(responseAPI);
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<?> getListCommentInteractionByCommentId(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @PathVariable Long commentId
    ){
        PageResponse<CommentInteractionResponse> response = commentInteractionQueryService.getListCommentInteractionByCommentId(page, size, sortBy, sortDir, commentId);
        BaseResponseAPI<PageResponse<CommentInteractionResponse>> responseAPI = new BaseResponseAPI<>(true,"Get list comment interaction by comment id: "+commentId,response,null);
        return ResponseEntity.ok(responseAPI);
    }


}
