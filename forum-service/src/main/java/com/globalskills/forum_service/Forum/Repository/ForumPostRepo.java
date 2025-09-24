package com.globalskills.forum_service.Forum.Repository;

import com.globalskills.forum_service.Forum.Entity.ForumPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForumPostRepo extends JpaRepository<ForumPost,Long> {
    Optional<ForumPost> findForumPostById(Long id);
}
