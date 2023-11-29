package com.api.TaveShot.global.oauth2;

import static com.api.TaveShot.global.constant.OauthConstant.AVATAR_URL_PATTERN;
import static com.api.TaveShot.global.constant.OauthConstant.EMAIL_PATTERN;
import static com.api.TaveShot.global.constant.OauthConstant.ID_PATTERN;
import static com.api.TaveShot.global.constant.OauthConstant.LOGIN_PATTERN;
import static com.api.TaveShot.global.constant.OauthConstant.NAME_PATTERN;

import java.util.Map;
import lombok.Builder;

@Builder
public record GithubUserInfo(Map<String, Object> userInfo) {

    public String getProfileImageUrl() {
        return (String) userInfo.get(AVATAR_URL_PATTERN);
    }

    public String getLoginId() {
        return (String) userInfo.get(LOGIN_PATTERN);
    }

    public String getName() {
        return (String) userInfo.get(NAME_PATTERN);
    }

    public String getMail() {
        return (String) userInfo.get(EMAIL_PATTERN);
    }

    public Long getId() {
        return (Long) userInfo.get(ID_PATTERN);
    }
}
