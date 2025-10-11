package com.globalskills.forum_service.Forum.Dto;

import com.globalskills.forum_service.Forum.Enum.TypeInteraction;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostInteractionRequest {
    TypeInteraction typeInteraction;
}
