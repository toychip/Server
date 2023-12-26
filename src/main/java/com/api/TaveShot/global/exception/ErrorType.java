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
    _POST_INVALID_TIER(BAD_REQUEST, "POST_4000", "게시글의 등급이 잘못되었습니다."),
    _POST_NOT_FOUND(NOT_FOUND, "POST_4040", "해당 게시글이 존재하지 않습니다."),
    _POST_USER_FORBIDDEN(FORBIDDEN, "POST_4030", "해당 게시글에 접근 권한이 없습니다."),


    // ------------------------------------------ S3 ------------------------------------------
    EXCEEDING_FILE_COUNT(BAD_REQUEST, "S4001", "사진 개수가 너무 많습니다."),
    S3_UPLOAD(INTERNAL_SERVER_ERROR, "S5001", "서버오류, S3 사진 업로드 에러입니다."),
    S3_CONNECT(INTERNAL_SERVER_ERROR, "S5002", "서버오류, S3 연결 에러입니다."),
    S3_CONVERT(INTERNAL_SERVER_ERROR, "S5003", "서버오류, S3 변환 에러입니다."),

    // ------------------------------------------ USER ------------------------------------------
    _USER_NOT_FOUND_BY_TOKEN(NOT_FOUND, "USER_4040", "제공된 토큰으로 사용자를 찾을 수 없습니다."),
    _UNAUTHORIZED(UNAUTHORIZED, "USER_4010", "로그인되지 않은 상태입니다."),
    _USER_NOT_FOUND_DB(NOT_FOUND, "USER_4041", "존재하지 않는 회원입니다."),


    // ------------------------------------------ SOLVED API ------------------------------------------
    _SOLVED_API_CONNECT_URI(INTERNAL_SERVER_ERROR, "SOLVED_5000", "Solved Api Uri 관련 오류입니다."),
    _SOLVED_API_CONNECT_HTTP(INTERNAL_SERVER_ERROR, "SOLVED_5001", "Solved Api 네트워크 문제, 서버 문제 또는 기타 입출력 관련 문제입니다."),
    _SOLVED_API_CONNECT_INTERRUPT(INTERNAL_SERVER_ERROR, "SOLVED_5002", "Solved Api 스레드가 대기, 수면 또는 다른 차단 작업 중에 인터럽트"),
    _SOLVED_API_NO_BIO_FOUND(BAD_REQUEST, "SOLVED_5003", "Solved Api로 부터 bio 정보를 받을 수 없습니다."),

    // ------------------------------------------ GITHUB API ------------------------------------------
    _GITHUB_REPO_INVALID(BAD_REQUEST, "GITHUB_4040", "Github 인증 Repository description이 잘못되었습니다."),
    _GITHUB_NAME_NOT_MATCH(BAD_REQUEST, "GITHUB_4041", "solvedApi 자기소개에 저장된 Github 이름과 ")


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
