package com.api.TaveShot.global.util;

import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.global.exception.ApiException;
import com.api.TaveShot.global.exception.ErrorType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("authentication = " + authentication.getName());
        System.out.println("authentication.getPrincipal() = " + authentication.getPrincipal());
        System.out.println("authentication.getAuthorities() = " + authentication.getAuthorities());


        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ApiException(ErrorType._UNAUTHORIZED);
        }

        Object principal = authentication.getPrincipal();

        return (Member) principal;
    }
}
