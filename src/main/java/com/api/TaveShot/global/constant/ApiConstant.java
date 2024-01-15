package com.api.TaveShot.global.constant;

import com.api.TaveShot.global.exception.ApiException;
import com.api.TaveShot.global.exception.ErrorType;

public final class ApiConstant {

    private ApiConstant() {
        throw new ApiException(ErrorType._CANT_TRANCE_INSTANCE);
    }

    public static String SOLVED_REQUEST_USER_BIO_URI = "https://solved.ac/api/v3/user/show";
    public static String GITHUB_URI = "https://api.github.com";
}
