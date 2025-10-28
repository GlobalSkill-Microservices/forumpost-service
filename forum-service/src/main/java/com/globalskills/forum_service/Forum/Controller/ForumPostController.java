package com.globalskills.forum_service.Forum.Controller;

import com.globalskills.forum_service.Common.BaseResponseAPI;
import com.globalskills.forum_service.Common.PageResponse;
import com.globalskills.forum_service.Forum.Dto.ForumPostRequest;
import com.globalskills.forum_service.Forum.Dto.ForumPostResponse;
import com.globalskills.forum_service.Forum.Service.Forum.ForumPostCommandService;
import com.globalskills.forum_service.Forum.Service.Forum.ForumPostQueryService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forum-post")
@SecurityRequirement(name = "api")
public class ForumPostController {

    @Autowired
    ForumPostCommandService forumPostCommandService;

    @Autowired
    ForumPostQueryService forumPostQueryService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ForumPostRequest request,
                                    @RequestParam(required = false) Long sharePostId,
                                    @Parameter(hidden = true)
                                    @RequestHeader(value = "X-User-ID",required = false) Long accountId){
        ForumPostResponse response = forumPostCommandService.create(request,sharePostId,accountId);
        BaseResponseAPI<ForumPostResponse> responseAPI = new BaseResponseAPI<>(true,"Created forum post successfully",response,null);
        return ResponseEntity.ok(responseAPI);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody ForumPostRequest request,
                                    @RequestParam Long id){
        ForumPostResponse response = forumPostCommandService.update(request, id);
        BaseResponseAPI<ForumPostResponse> responseAPI = new BaseResponseAPI<>(true,"Updated forum post successfully",response,null);
        return ResponseEntity.ok(responseAPI);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestParam Long id){
        forumPostCommandService.delete(id);
        BaseResponseAPI<?> responseAPI = new BaseResponseAPI<>(true,"Deleted forum post successfully",null,null);
        return ResponseEntity.ok(responseAPI);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getForumPostById(@PathVariable Long id){
        ForumPostResponse response = forumPostQueryService.getForumPostById(id);
        BaseResponseAPI<ForumPostResponse> responseAPI = new BaseResponseAPI<>(true,"Get forum post successfully",response,null);
        return ResponseEntity.ok(responseAPI);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getListForumByUserId(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) Long accountId,
            @Parameter(hidden = true)
            @RequestHeader(value = "X-User-ID",required = false) Long currentAccountId){
        Long id = (accountId != null) ? accountId : currentAccountId;
        PageResponse<ForumPostResponse> response = forumPostQueryService.getListForumByAccountId(page, size, sortBy, sortDir, id);
        BaseResponseAPI<PageResponse<ForumPostResponse>> responseAPI = new BaseResponseAPI<>(true,"Get list forum post from accountId: "+ id,response,null);
        return ResponseEntity.ok(responseAPI);
    }

    @GetMapping
    public ResponseEntity<?> getAllListForum(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ){
        PageResponse<ForumPostResponse> response = forumPostQueryService.getAllListForum(page, size, sortBy, sortDir);
        BaseResponseAPI<PageResponse<ForumPostResponse>> responseAPI = new BaseResponseAPI<>(true,"Get all forum post",response,null);
        return ResponseEntity.ok(responseAPI);
    }

    @GetMapping("/shared/{forumPostId}")
    public ResponseEntity<?> getListShareOfPost(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @PathVariable Long forumPostId
    ){
        PageResponse<ForumPostResponse> response = forumPostQueryService.getListShareOfPost(forumPostId, page, size, sortBy, sortDir);
        BaseResponseAPI<PageResponse<ForumPostResponse>> responseAPI = new BaseResponseAPI<>(true,"Get all forum post",response,null);
        return ResponseEntity.ok(responseAPI);
    }

    @GetMapping("/trending-post")
    public ResponseEntity<?> getListTrendingPost(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ){
        PageResponse<ForumPostResponse> response = forumPostQueryService.getTrendingPosts(page, size, sortBy, sortDir);
        BaseResponseAPI<PageResponse<ForumPostResponse>> responseAPI = new BaseResponseAPI<>(true,"Get trending forum post successfully",response,null);
        return ResponseEntity.ok(responseAPI);
    }
}
