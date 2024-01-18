package com.api.TaveShot.global.constant;

import static com.api.TaveShot.global.exception.ErrorType._CANT_TRANCE_INSTANCE;

import com.api.TaveShot.global.exception.ApiException;

public final class OauthConstant {

    private OauthConstant() {
        throw new ApiException(_CANT_TRANCE_INSTANCE);
    }

    public static final String ID_PATTERN = "id";
    public static final String PROFILE_IMAGE_URL_PATTERN = "avatar_url";
    public static final String LOGIN_PATTERN = "login";
    // ToDo 추후 Github에서 제공하는 Name 을 사용할지 결정
    public static final String NAME_PATTERN = "name";
    public static final String EMAIL_PATTERN = "email";
    public static final long ACCESS_TOKEN_VALID_TIME = 15 * 60 * 1000L;
    // ToDo 배포 후 프론트 url로 수정, 프론트 개발을 위해 로컬로 임시 설정
    public static final String REDIRECT_URL = "http://localhost:3000";
    public static final int MAX_PAGE_SIZE = 100;
    public static final int MAX_PAGE_NUMBER = 1000;

}
