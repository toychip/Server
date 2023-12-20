package com.api.TaveShot.domain.post.domain;

import com.api.TaveShot.global.exception.ApiException;
import com.api.TaveShot.global.exception.ErrorType;

public enum PostTier {
    BRONZE_SILVER("BronzeSilver"),
    GOLD("Gold"),
    PLATINUM("Platinum"),
    HIGH("High");

    private final String tier;

    PostTier(String tier) {
        this.tier = tier;
    }

    // 문자열을 enum으로 변환하는 메서드
    public static PostTier findTier(String input) {
        for (PostTier post : PostTier.values()) {
            if (post.tier.equalsIgnoreCase(input)) {
                return post;
            }
        }
        throw new ApiException(ErrorType._POST_INVALID_TIER);
    }

    public static boolean isHighTier(PostTier tier) {
        return tier == HIGH;
    }
}
