package com.globalskills.forum_service.Forum.Controller;

import com.globalskills.forum_service.Common.BaseResponseAPI;
import com.globalskills.forum_service.Forum.Dto.ShareResponse;
import com.globalskills.forum_service.Forum.Service.ShareCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/share")
@CrossOrigin("*")
public class ShareController {

    @Autowired
    ShareCommandService shareCommandService;

    @PostMapping
    public ResponseEntity<?> create(Long postId,Long accountId){
        ShareResponse response = shareCommandService.create(postId, accountId);
        BaseResponseAPI<ShareResponse> responseAPI = new BaseResponseAPI<>(true,"Share successfully",response,null);
        return ResponseEntity.ok(responseAPI);
    }



}
