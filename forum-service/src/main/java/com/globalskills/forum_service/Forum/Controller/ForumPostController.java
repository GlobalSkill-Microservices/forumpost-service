package com.globalskills.forum_service.Forum.Controller;

import com.globalskills.forum_service.Common.BaseResponseAPI;
import com.globalskills.forum_service.Forum.Dto.ForumPostRequest;
import com.globalskills.forum_service.Forum.Dto.ForumPostResponse;
import com.globalskills.forum_service.Forum.Service.ForumPostCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/forum-post")
@CrossOrigin("*")
public class ForumPostController {

    @Autowired
    ForumPostCommandService forumPostCommandService;


    public ResponseEntity<?> create(@RequestBody ForumPostRequest request, @RequestParam Long accountId){
        ForumPostResponse response = forumPostCommandService.create(request,accountId);
        BaseResponseAPI<ForumPostResponse> responseAPI = new BaseResponseAPI<>(true,"Created forum post successfully",response,null);
        return ResponseEntity.ok(responseAPI);
    }

    public ResponseEntity<?> update(@RequestBody ForumPostRequest request,@RequestParam Long id){
        ForumPostResponse response = forumPostCommandService.update(request, id);
        BaseResponseAPI<ForumPostResponse> responseAPI = new BaseResponseAPI<>(true,"Updated forum post successfully",response,null);
        return ResponseEntity.ok(responseAPI);
    }

    public ResponseEntity<?> delete(@RequestParam Long id){
        forumPostCommandService.delete(id);
        BaseResponseAPI<?> responseAPI = new BaseResponseAPI<>(true,"Deleted forum post successfully",null,null);
        return ResponseEntity.ok(responseAPI);
    }
}
