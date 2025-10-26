package com.globalskills.forum_service.Forum.Repository;

import com.globalskills.forum_service.Forum.Entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Long> {
    Optional<Comment> findCommentById(Long id);

    Page<Comment> findAllByPost_Id(PageRequest pageRequest, Long forumPostId);

    Page<Comment> findAllByParent_Id(PageRequest pageRequest, Long parentCommentId);

    Page<Comment> findAllByPost_IdAndParentIsNull(PageRequest pageRequest, Long forumPostId);

    List<Comment> findAllByParent_IdIn(List<Long> parentIds);
}
