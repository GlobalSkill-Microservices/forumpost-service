package com.globalskills.forum_service.Forum.Repository;

import com.globalskills.forum_service.Forum.Entity.PostInteraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostInteractionRepo extends JpaRepository<PostInteraction,Long> {
    Optional<PostInteraction> findPostInteractionById(Long id);
}
