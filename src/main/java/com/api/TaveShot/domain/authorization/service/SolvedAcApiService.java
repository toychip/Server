package com.api.TaveShot.domain.authorization.service;

import static com.api.TaveShot.global.constant.ApiConstant.SOLVED_REQUEST_USER_BIO_URI;

import com.api.TaveShot.domain.authorization.dto.SolvedUserInfo;
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

    public SolvedUserInfo getUserInfoFromSolvedAc(String handle) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/{handle}").build(handle))
                .retrieve()
                .bodyToMono(SolvedUserInfo.class)
                .block();

    }

}
