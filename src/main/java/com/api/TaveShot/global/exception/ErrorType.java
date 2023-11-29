package com.api.TaveShot.global.exception;

import static org.springframework.http.HttpStatus.*;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ErrorType {

    /**
     * Error Message Convention
     *
     * name : _(head) + Error Name status : HttpStatus
     *
     * errorCode : 400번 오류인 상황이 여러개 올텐데, 4001, 4002, 4003.. 이런식으로 설정 (해당 오류들은 MEMBER 와 관련된 400 오류들)
     * ex) Member Error, Http Status Code: 400 -> MEMBER_4000
     *
     * message : 사람이 알아볼 수 있도록 작성
     * ex) "인증이 필요합니다."
     */

    _CANT_TRANCE_INSTANCE(INTERNAL_SERVER_ERROR, "SERVER_5000", "상수는 인스턴스화 할 수 없습니다."),
    _SERVER_USER_NOT_FOUND(INTERNAL_SERVER_ERROR, "SERVER_5001", "로그인이 성공한 소셜 로그인 유저가 DB에 존재하지 않을 수 없습니다."),
    _TOKEN_EXPIRED(UNAUTHORIZED, "JWT_4010", "Jwt Token의 유효 기간이 만료되었습니다.")
    // 각종 에러들
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;

    ErrorType(HttpStatus status, String errorCode, String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
