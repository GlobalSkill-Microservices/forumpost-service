package com.globalskills.forum_service.Forum.Dto;

import com.globalskills.forum_service.Forum.Enum.TypeInteraction;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentInteractionResponse {
    TypeInteraction typeInteraction;
}
