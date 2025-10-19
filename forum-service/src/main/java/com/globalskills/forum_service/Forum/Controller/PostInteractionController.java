package com.globalskills.forum_service.Forum.Controller;


import com.globalskills.forum_service.Common.BaseResponseAPI;
import com.globalskills.forum_service.Common.PageResponse;
import com.globalskills.forum_service.Forum.Dto.PostInteractionRequest;
import com.globalskills.forum_service.Forum.Dto.PostInteractionResponse;
import com.globalskills.forum_service.Forum.Service.Post.PostInteractionCommandService;
import com.globalskills.forum_service.Forum.Service.Post.PostInteractionQueryService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post-interaction")
@SecurityRequirement(name = "api")
public class PostInteractionController {

    @Autowired
    PostInteractionCommandService postInteractionCommandService;

    @Autowired
    PostInteractionQueryService postInteractionQueryService;

    @GetMapping("/forum-post/{forumPostId}/is-react")
    public ResponseEntity<?> checkPostReact(@PathVariable Long forumPostId,
                                            @Parameter(hidden = true)
                                            @RequestHeader(value = "X-User-ID",required = false) Long accountId){
        Boolean isReact = postInteractionQueryService.isReact(forumPostId, accountId);
        BaseResponseAPI<Boolean> responseAPI = new BaseResponseAPI<>(true,"Check react",isReact,null);
        return ResponseEntity.ok(responseAPI);
    }

    @PostMapping("/forum-post/{forumPostId}")
    public ResponseEntity<?> create(@RequestBody PostInteractionRequest request,
                                    @PathVariable Long forumPostId,
                                    @Parameter(hidden = true)
                                    @RequestHeader(value = "X-User-ID",required = false) Long accountId){
        PostInteractionResponse response = postInteractionCommandService.create(request, forumPostId, accountId);
        BaseResponseAPI<PostInteractionResponse> responseAPI = new BaseResponseAPI<>(true,"Create post interaction successfully",response,null);
        return ResponseEntity.ok(responseAPI);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody PostInteractionRequest request,
                                    @PathVariable Long id){
        PostInteractionResponse response = postInteractionCommandService.update(request, id);
        BaseResponseAPI<PostInteractionResponse> responseAPI = new BaseResponseAPI<>(true,"Update post interaction successfully",response,null);
        return ResponseEntity.ok(responseAPI);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        postInteractionCommandService.delete(id);
        BaseResponseAPI<?> responseAPI = new BaseResponseAPI<>(true,"Delete post interaction successfully",null,null);
        return ResponseEntity.ok(responseAPI);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostInteractionById(@PathVariable Long id){
        PostInteractionResponse response = postInteractionQueryService.getPostInteractionById(id);
        BaseResponseAPI<PostInteractionResponse> responseAPI = new BaseResponseAPI<>(true,"Get post interaction successfully",response,null);
        return ResponseEntity.ok(responseAPI);
    }

    @GetMapping("/forum-post/{forumPostId}")
    public ResponseEntity<?> getListPostInteractionByForumPostId(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @PathVariable Long forumPostId
    ){
        PageResponse<PostInteractionResponse> pageResponse = postInteractionQueryService.getListPostInteractionByForumPostId(forumPostId,page, size, sortBy, sortDir);
        BaseResponseAPI<PageResponse<PostInteractionResponse>> responseAPI = new BaseResponseAPI<>(true,"Get list post interaction successfully",pageResponse,null);
        return ResponseEntity.ok(responseAPI);
    }
}
