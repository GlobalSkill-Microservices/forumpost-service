package com.globalskills.forum_service.Forum.Repository;

import com.globalskills.forum_service.Forum.Entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShareRepo extends JpaRepository<Share,Long> {

    Optional<Share> findShareById(Long id);
}
