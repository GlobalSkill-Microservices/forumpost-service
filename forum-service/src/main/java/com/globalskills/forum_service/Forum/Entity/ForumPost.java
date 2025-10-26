package com.globalskills.forum_service.Forum.Entity;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;

    @Column(columnDefinition = "NVARCHAR(5000)")
    String content;

    Long accountId;

    Date createdAt;

    Date updatedAt;

    Boolean isPublic;

    Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "shared_post_id")
    ForumPost sharedPost;

    @OneToMany(mappedBy = "sharedPost", fetch = FetchType.LAZY)
    List<ForumPost> shares;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comment> comments;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<PostInteraction> interactions;

    Integer commentCount = 0;
    Integer shareCount = 0;
    Integer interactionCount = 0;
}
