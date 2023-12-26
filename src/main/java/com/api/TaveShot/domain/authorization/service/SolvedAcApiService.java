package com.api.TaveShot.domain.authorization.service;

import static com.api.TaveShot.global.constant.ApiConstant.SOLVED_REQUEST_USER_BIO_URI;

import com.api.TaveShot.domain.authorization.dto.SolvedAcUserInfo;
import com.api.TaveShot.global.exception.ApiException;
import com.api.TaveShot.global.exception.ErrorType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SolvedAcApiService {

    private final WebClient webClient;

    public SolvedAcApiService() {
        this.webClient = WebClient.builder()
                .baseUrl(SOLVED_REQUEST_USER_BIO_URI)
                .build();
    }

    public String getUserInfoFromSolvedAc(String handle) {
        SolvedAcUserInfo userInfo = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/{handle}").build(handle))
                .retrieve()
                .bodyToMono(SolvedAcUserInfo.class)
                .block();

        return bio(userInfo);
    }

    private String bio(SolvedAcUserInfo userInfo) {
        if (userInfo != null && userInfo.bio() != null) {
            return userInfo.bio();
        }
        throw new ApiException(ErrorType._SOLVED_API_NO_BIO_FOUND);
    }
}
