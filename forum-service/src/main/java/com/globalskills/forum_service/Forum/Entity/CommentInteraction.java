package com.globalskills.forum_service.Forum.Entity;

import com.globalskills.forum_service.Forum.Enum.TypeInteraction;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentInteraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    TypeInteraction typeInteraction;

    Long accountId;

    Date createdAt;

    Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    Comment comment;

}
