package com.api.TaveShot.domain.post.view.repository;

import com.api.TaveShot.domain.post.view.domain.ViewHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewHistoryRepository extends JpaRepository<ViewHistory, Long> {
    boolean existsByPostIdAndMemberId(Long postId, Long memberId);
}
