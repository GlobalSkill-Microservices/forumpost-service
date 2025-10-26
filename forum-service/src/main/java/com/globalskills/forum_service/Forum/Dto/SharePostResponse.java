package com.globalskills.forum_service.Forum.Dto;

import com.globalskills.forum_service.Common.AccountDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SharePostResponse {
    Long id;
    String title;
    String content;
    AccountDto accountId;
    Integer commentCount;
    Integer shareCount;
    Integer interactionCount;
}
