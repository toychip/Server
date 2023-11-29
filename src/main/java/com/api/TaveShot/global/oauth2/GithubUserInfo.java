package com.api.TaveShot.global.oauth2;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public record GithubUserInfo(Map<String, Object> userInfo) {

    private static final String AVATAR_URL_PATTERN = "avatar_url";
    private static final String LOGIN_PATTERN = "login";
    private static final String NAME_PATTERN = "name";
    private static final String EMAIL_PATTERN = "email";

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
}
