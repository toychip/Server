package com.api.TaveShot.global.security.oauth2;

import static com.api.TaveShot.global.constant.OauthConstant.PROFILE_IMAGE_URL_PATTERN;
import static com.api.TaveShot.global.constant.OauthConstant.EMAIL_PATTERN;
import static com.api.TaveShot.global.constant.OauthConstant.ID_PATTERN;
import static com.api.TaveShot.global.constant.OauthConstant.LOGIN_PATTERN;

import java.util.Map;
import lombok.Builder;

@Builder
public record GithubUserInfo(Map<String, Object> userInfo) {

    public String getLoginId() {
        return (String) userInfo.get(LOGIN_PATTERN);
    }


    public Integer getId() {
        return (Integer) userInfo.get(ID_PATTERN);
    }

    public String getMail() {
        return (String) userInfo.get(EMAIL_PATTERN);
    }

    public String getProfileImageUrl() {
        return (String) userInfo.get(PROFILE_IMAGE_URL_PATTERN);
    }
}
