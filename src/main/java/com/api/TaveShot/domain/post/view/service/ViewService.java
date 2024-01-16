package com.api.TaveShot.domain.post.view.service;

import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.post.view.domain.ViewHistory;
import com.api.TaveShot.domain.post.view.repository.ViewHistoryRepository;
import com.api.TaveShot.global.util.SecurityUtil;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViewService {

    private final ViewHistoryRepository viewHistoryRepository;

    public boolean checkAndAddViewHistory(final Long postId) {
        Long currentMemberId = getCurrentMember().getId();
        boolean isAlreadyViewed = isAlreadyViewed(postId, currentMemberId);
        if (!isAlreadyViewed) {
            register(postId, currentMemberId);
            return false;
        }
        return true;
    }

    private Member getCurrentMember() {
        return SecurityUtil.getCurrentMember();
    }

    private boolean isAlreadyViewed(final Long postId, final Long userId) {
        return viewHistoryRepository.existsByPostIdAndMemberId(postId, userId);
    }

    private void register(final Long postId, final Long memberId) {
        ViewHistory viewHistory = ViewHistory.builder()
                .postId(postId)
                .memberId(memberId)
                .viewTime(LocalDateTime.now())
                .build();

        viewHistoryRepository.save(viewHistory);
    }
}
