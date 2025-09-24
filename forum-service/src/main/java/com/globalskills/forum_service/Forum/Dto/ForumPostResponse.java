package com.globalskills.forum_service.Forum.Dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ForumPostResponse {
    Long id;
    String content;
    Long accountId;
    Date createdAt;
    Date updatedAt;
    Boolean isPublic;
    Boolean isDeleted;
}
