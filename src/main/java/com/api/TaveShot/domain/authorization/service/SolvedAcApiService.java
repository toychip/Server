package com.api.TaveShot.domain.authorization.service;

import static com.api.TaveShot.global.constant.ApiConstant.SOLVED_REQUEST_USER_BIO_URI;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.api.TaveShot.domain.authorization.dto.SolvedUserInfo;
import com.api.TaveShot.global.exception.ApiException;
import com.api.TaveShot.global.exception.ErrorType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class SolvedAcApiService {

    private final WebClient webClient;

    public SolvedAcApiService() {
        this.webClient = WebClient.builder()
                .baseUrl(SOLVED_REQUEST_USER_BIO_URI)
                .build();
    }

    public SolvedUserInfo getUserInfoFromSolvedAc(final String handle) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/{handle}").build(handle))
                    .retrieve()
                    .onStatus(BAD_REQUEST::equals, response ->
                            Mono.error(new ApiException(ErrorType._SOLVED_INVALID_REQUEST))) // 400 오류 처리
                    .onStatus(NOT_FOUND::equals, response ->
                            Mono.error(new ApiException(ErrorType._SOLVED_NOT_FOUND))) // 404 오류 처리
                    .onStatus(INTERNAL_SERVER_ERROR::equals, response ->
                            Mono.error(new ApiException(ErrorType._SOLVED_INTERNAL_SERVER_ERROR))) // 500 오류 처리
                    .bodyToMono(SolvedUserInfo.class)
                    .block();
        } catch (WebClientResponseException wcrE) {
            throw new ApiException(ErrorType._WEB_CLIENT_ERROR);
        }
    }

}
