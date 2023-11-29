package com.api.TaveShot.global.constant;

public final class OauthConstant {

    private OauthConstant() {
        throw new IllegalArgumentException("인스턴스화 불가");
    }

    public static final String ID_PATTERN = "id";
    public static final String PROFILE_IMAGE_URL_PATTERN = "avatar_url";
    public static final String LOGIN_PATTERN = "login";
    // ToDo 추후 Github에서 제공하는 Name 을 사용할지 결정
    public static final String NAME_PATTERN = "name";
    public static final String EMAIL_PATTERN = "email";
    public static final long ACCESS_TOKEN_VALID_TIME = 15 * 60 * 1000L;
    public static final String REDIRECT_URL = "http://localhost:5173";

}
