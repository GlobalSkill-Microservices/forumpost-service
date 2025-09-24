package com.globalskills.forum_service.Forum.Entity;

import com.globalskills.forum_service.Forum.Enum.TypeInteraction;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostInteraction {
    @Id
    Long id;

    @Enumerated(EnumType.STRING)
    TypeInteraction typeInteraction;

    Long accountId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    ForumPost post;


}
