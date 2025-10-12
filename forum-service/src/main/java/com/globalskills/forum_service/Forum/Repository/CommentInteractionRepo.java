package com.globalskills.forum_service.Forum.Repository;

import com.globalskills.forum_service.Forum.Entity.CommentInteraction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentInteractionRepo extends JpaRepository<CommentInteraction,Long> {
    Optional<CommentInteraction> findCommentInteractionById(Long id);

    Page<CommentInteraction> findAllByComment_Id(PageRequest pageRequest, Long commentId);

    CommentInteraction findByComment_IdAndAccountId(Long commentId, Long accountId);
}
