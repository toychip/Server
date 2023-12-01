package com.api.TaveShot.global.util;

import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.global.exception.ApiException;
import com.api.TaveShot.global.exception.ErrorType;
import com.api.TaveShot.global.security.oauth2.CustomOauth2User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ApiException(ErrorType._UNAUTHORIZED);
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomOauth2User) {
            return ((CustomOauth2User) principal).getMember();
        }

        throw new ApiException(ErrorType._USER_NOT_FOUND_DB);
    }
}
