package com.api.TaveShot.domain.post.view.service;

import com.api.TaveShot.domain.post.view.domain.ViewHistory;
import com.api.TaveShot.domain.post.view.repository.ViewHistoryRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ViewService {

    private final ViewHistoryRepository viewHistoryRepository;

    @Transactional
    public void checkAndAddViewHistory(Long postId, Long userId) {
        boolean isAlreadyViewed = isAlreadyViewed(postId, userId);
        if (!isAlreadyViewed) {
            register(postId, userId);
        }
    }

    private boolean isAlreadyViewed(Long postId, Long userId) {
        return viewHistoryRepository.existsByPostIdAndUserId(postId, userId);
    }

    public void register(Long postId, Long memberId) {
        ViewHistory viewHistory = ViewHistory.builder()
                .postId(postId)
                .userId(memberId)
                .viewTime(LocalDateTime.now())
                .build();

        viewHistoryRepository.save(viewHistory);
    }
}
