package com.globalskills.forum_service.Forum.Repository;

import com.globalskills.forum_service.Forum.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Long> {
    Optional<Comment> findCommentById(Long id);
}
