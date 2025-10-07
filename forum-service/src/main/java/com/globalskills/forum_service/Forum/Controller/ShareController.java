package com.globalskills.forum_service.Forum.Controller;

import com.globalskills.forum_service.Common.BaseResponseAPI;
import com.globalskills.forum_service.Forum.Dto.ShareResponse;
import com.globalskills.forum_service.Forum.Service.ShareCommandService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/share")
@CrossOrigin("*")
@SecurityRequirement(name = "api")
public class ShareController {

    @Autowired
    ShareCommandService shareCommandService;

    @PostMapping("/{postId}")
    public ResponseEntity<?> create(@PathVariable Long postId,
                                    @Parameter(hidden = true)
                                    @RequestHeader(value = "X-User-ID",required = false) Long accountId){
        ShareResponse response = shareCommandService.create(postId, accountId);
        BaseResponseAPI<ShareResponse> responseAPI = new BaseResponseAPI<>(true,"Share successfully",response,null);
        return ResponseEntity.ok(responseAPI);
    }



}
