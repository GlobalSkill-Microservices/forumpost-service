package com.globalskills.forum_service.Forum.Dto;

import com.globalskills.forum_service.Common.AccountDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParentCommentResponse {
    Long id;
    Long postId;
    AccountDto accountId;
    String content;
    Date createdAt;
    Date updatedAt;

    List<ReplyCommentResponse> replyCommentResponses;
}
