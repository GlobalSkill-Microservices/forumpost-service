package com.globalskills.forum_service.Forum.Exception;

import com.globalskills.forum_service.Common.BaseException;
import org.springframework.http.HttpStatus;

public class PostInteractionException extends BaseException {
    public PostInteractionException(String message, HttpStatus status) {
        super(message,status);
    }
}
