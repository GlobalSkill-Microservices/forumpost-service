package com.globalskills.forum_service.Forum.Controller;


import com.globalskills.forum_service.Common.BaseResponseAPI;
import com.globalskills.forum_service.Forum.Dto.PostInteractionRequest;
import com.globalskills.forum_service.Forum.Dto.PostInteractionResponse;
import com.globalskills.forum_service.Forum.Service.PostInteractionCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post-interaction")
@CrossOrigin("*")
public class PostInteractionController {

    @Autowired
    PostInteractionCommandService postInteractionCommandService;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody PostInteractionRequest request, @RequestParam Long postId,@RequestParam Long accountId){
        PostInteractionResponse response = postInteractionCommandService.create(request, postId, accountId);
        BaseResponseAPI<PostInteractionResponse> responseAPI = new BaseResponseAPI<>(true,"Create post interaction successfully",response,null);
        return ResponseEntity.ok(responseAPI);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody PostInteractionRequest request,@RequestParam Long id){
        PostInteractionResponse response = postInteractionCommandService.update(request, id);
        BaseResponseAPI<PostInteractionResponse> responseAPI = new BaseResponseAPI<>(true,"Update post interaction successfully",response,null);
        return ResponseEntity.ok(responseAPI);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestParam Long id){
        postInteractionCommandService.delete(id);
        BaseResponseAPI<?> responseAPI = new BaseResponseAPI<>(true,"Delete post interaction successfully",null,null);
        return ResponseEntity.ok(responseAPI);
    }
}
