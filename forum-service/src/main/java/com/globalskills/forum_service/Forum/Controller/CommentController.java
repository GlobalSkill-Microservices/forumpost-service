package com.globalskills.forum_service.Forum.Controller;

import com.globalskills.forum_service.Common.BaseResponseAPI;
import com.globalskills.forum_service.Forum.Dto.CommentRequest;
import com.globalskills.forum_service.Forum.Dto.CommentResponse;
import com.globalskills.forum_service.Forum.Service.CommentCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@CrossOrigin("*")
public class CommentController {

    @Autowired
    CommentCommandService commentCommandService;

    @
    public ResponseEntity<?> create(@RequestBody CommentRequest request,@RequestParam Long accountId){
        CommentResponse response = commentCommandService.create(request, accountId);
        BaseResponseAPI<CommentResponse> responseAPI = new BaseResponseAPI<>(true,"Create comment successfully",response,null);
        return ResponseEntity.ok(responseAPI);
    }

    public ResponseEntity<?> update(@RequestBody CommentRequest request,@RequestParam Long id){
        CommentResponse response = commentCommandService.update(request, id);
        BaseResponseAPI<CommentResponse> responseAPI = new BaseResponseAPI<>(true,"Update comment successfully",response,null);
        return ResponseEntity.ok(responseAPI);
    }

}
