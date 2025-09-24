package com.globalskills.forum_service.Forum.Exception;

import com.globalskills.forum_service.Common.BaseException;
import org.springframework.http.HttpStatus;

public class CommentException extends BaseException {
    public CommentException(String message, HttpStatus status) {
        super(message,status);
    }
}
