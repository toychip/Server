package com.api.TaveShot.domain.recommend.service;

import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.recommend.dto.UserCrawlingDto;
import com.api.TaveShot.domain.recommend.dto.RecProResponseDto;
import com.api.TaveShot.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecommendService {

    private final CrawlingService crawlingService;

    @Value("${lambda.secret.url1}")
    private String lambda1;

    // 문제 기반 추천 서비스
    public RecProResponseDto getListByProblem() throws IOException {
        Member currentMember = getCurrentMember();
        String bojName = currentMember.getBojName();
        UserCrawlingDto dto = crawlingService.getUserInfo(bojName);

        WebClient webClient = WebClient.builder()
                .baseUrl(lambda1)
                .build();

        RecProResponseDto proList = webClient.post()
                .body(BodyInserters.fromValue(dto))
                .retrieve()
                .bodyToMono(RecProResponseDto.class)
                .block();

        return proList;
    }

    private Member getCurrentMember(){
        return SecurityUtil.getCurrentMember();
    }

    // 사용자 기반 추천 서비스
//    public ProblemResponseDto getListByUser(){
//
//    }


}
