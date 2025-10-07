package com.globalskills.forum_service.Forum.Controller;

import com.globalskills.forum_service.Common.BaseResponseAPI;
import com.globalskills.forum_service.Forum.Dto.CommentRequest;
import com.globalskills.forum_service.Forum.Dto.CommentResponse;
import com.globalskills.forum_service.Forum.Service.CommentCommandService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@CrossOrigin("*")
public class CommentController {

    @Autowired
    CommentCommandService commentCommandService;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody CommentRequest request,
                                    @Parameter(hidden = true)
                                    @RequestHeader(value = "X-User-ID",required = false) Long accountId){
        CommentResponse response = commentCommandService.create(request, accountId);
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

}
