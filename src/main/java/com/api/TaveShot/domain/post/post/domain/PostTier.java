package com.api.TaveShot.domain.post.post.domain;

import static com.api.TaveShot.domain.Member.domain.Tier.*;

import com.api.TaveShot.domain.Member.domain.Tier;
import com.api.TaveShot.global.exception.ApiException;
import com.api.TaveShot.global.exception.ErrorType;
import java.util.Arrays;
import java.util.List;
import lombok.ToString;

@ToString
public enum PostTier {
    POST_BRONZE_SILVER("BronzeSilver", Arrays.asList(BRONZE, SILVER, GOLD, PLATINUM, DIAMOND, RUBY, MASTER)),
    POST_GOLD("Gold", Arrays.asList(GOLD, PLATINUM, DIAMOND, RUBY, MASTER)),
    POST_PLATINUM("Platinum", Arrays.asList(PLATINUM, DIAMOND, RUBY, MASTER)),
    POST_HIGH("High", Arrays.asList(DIAMOND, RUBY, MASTER));

    private final String tier;
    private final List<Tier> associatedTiers;

    PostTier(String tier, List<Tier> associatedTiers) {
        this.tier = tier;
        this.associatedTiers = associatedTiers;
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

    public boolean containsTier(Tier tier) {
        return associatedTiers.contains(tier);
    }
}
