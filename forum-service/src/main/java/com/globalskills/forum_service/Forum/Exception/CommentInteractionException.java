package com.globalskills.forum_service.Forum.Exception;

import com.globalskills.forum_service.Common.BaseException;
import org.springframework.http.HttpStatus;

public class CommentInteractionException extends BaseException {
    public CommentInteractionException(String message, HttpStatus status) {
        super(message,status);
    }
}
