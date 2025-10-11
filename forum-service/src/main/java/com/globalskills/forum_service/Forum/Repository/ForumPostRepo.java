package com.globalskills.forum_service.Forum.Repository;

import com.globalskills.forum_service.Forum.Entity.ForumPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForumPostRepo extends JpaRepository<ForumPost,Long> {
    Optional<ForumPost> findForumPostById(Long id);

    Page<ForumPost> findAllByAccountId(PageRequest pageRequest, Long accountId);

    Page<ForumPost> findAllBySharedPost_Id(PageRequest pageRequest, Long forumPostId);
}
