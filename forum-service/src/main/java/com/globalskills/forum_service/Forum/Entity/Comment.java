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
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long accountId;
    String content;
    Date createdAt;
    Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "post_id")
    ForumPost post;

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<CommentInteraction> interactions;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    Comment parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    List<Comment> replies;

    Integer commentInteractionCount = 0;
}
