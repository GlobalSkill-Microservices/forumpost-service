package com.globalskills.forum_service.Forum.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ForumPost {
    @Id
    Long id;
    String content;
    Long accountId;
    Date createdAt;
    Date updatedAt;
    Boolean isPublic;
    Boolean isDeleted;

    @OneToMany(mappedBy = "post")
    List<Comment> comments;

    @OneToMany(mappedBy = "post")
    List<Share> shares;

    @OneToMany(mappedBy = "post")
    List<PostInteraction> interactions;
}
