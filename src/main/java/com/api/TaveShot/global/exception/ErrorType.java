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

    // ------------------------------------------ SERVER ------------------------------------------
    _CANT_TRANCE_INSTANCE(INTERNAL_SERVER_ERROR, "SERVER_5000", "상수는 인스턴스화 할 수 없습니다."),
    _SERVER_USER_NOT_FOUND(INTERNAL_SERVER_ERROR, "SERVER_5001", "로그인이 성공한 소셜 로그인 유저가 DB에 존재하지 않을 수 없습니다."),


    // ---------------------------------------- JWT TOKEN ----------------------------------------
    _JWT_PARSING_ERROR(BAD_REQUEST, "JWT_4001", "JWT 토큰 파싱 중 오류가 발생했습니다."),
    _JWT_EXPIRED(UNAUTHORIZED, "JWT_4010", "Jwt Token의 유효 기간이 만료되었습니다."),


    // ------------------------------------------ POST ------------------------------------------
    _POST_NOT_FOUND(NOT_FOUND, "POST_4040", "해당 게시글이 존재하지 않습니다."),


    // ------------------------------------------ USER ------------------------------------------
    _USER_NOT_FOUND_BY_TOKEN(NOT_FOUND, "USER_4040", "제공된 토큰으로 사용자를 찾을 수 없습니다."),
    _UNAUTHORIZED(UNAUTHORIZED, "USER_4010", "로그인되지 않은 상태입니다."),
    _USER_NOT_FOUND_DB(NOT_FOUND, "USER_4041", "존재하지 않는 회원입니다.")
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
