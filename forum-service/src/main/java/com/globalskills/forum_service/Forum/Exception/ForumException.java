package com.globalskills.forum_service.Forum.Exception;

import com.globalskills.forum_service.Common.BaseException;
import org.springframework.http.HttpStatus;

public class ForumException extends BaseException {
    public ForumException(String message, HttpStatus status) {
        super(message,status);
    }
}
