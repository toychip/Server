package com.api.TaveShot.domain.post.post;

import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.Member.domain.Tier;
import com.api.TaveShot.domain.post.post.domain.PostTier;
import com.api.TaveShot.global.exception.ApiException;
import com.api.TaveShot.global.exception.ErrorType;
import org.springframework.stereotype.Service;

@Service
public class PostValidator {

    public void validateAuthority(final PostTier postTier, final Member currentMember) {
        Tier memberTier = currentMember.getTier();

        boolean isContains = postTier.containsTier(memberTier);
        if (!isContains) {
            throw new ApiException(ErrorType._POST_USER_FORBIDDEN);
        }
    }
}
