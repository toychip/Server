package com.api.TaveShot.domain.recommend.service;

import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.Member.repository.MemberRepository;
import com.api.TaveShot.domain.recommend.domain.TierCount;
import com.api.TaveShot.domain.recommend.dto.RecResponseDto;
import com.api.TaveShot.domain.recommend.dto.UserCrawlingDto;
import com.api.TaveShot.domain.recommend.dto.RecProResponseDto;
import com.api.TaveShot.domain.recommend.repository.TierCountRepository;
import com.api.TaveShot.global.exception.ApiException;
import com.api.TaveShot.global.exception.ErrorType;
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
    private final MemberRepository memberRepository;
    private final TierCountRepository tierCountRepository;

    @Value("${lambda.secret.url1}")
    private String lambda1;

    @Value("${lambda.secret.url2}")
    private String lambda2;

    // 사용자 기반 추천 서비스
    public RecResponseDto getListByUser() throws IOException {
        Member currentMember1 = getCurrentMember();
        String bojName = currentMember1.getBojName();

        UserCrawlingDto dto = crawlingService.getUserInfo(bojName);

        WebClient webClient = WebClient.builder()
                .baseUrl(lambda1)
                .build();

        RecProResponseDto proList = webClient.post()
                .body(BodyInserters.fromValue(dto))
                .retrieve()
                .bodyToMono(RecProResponseDto.class)
                .block();

        Integer tierCount = tierCountRepository.findByTier(Integer.parseInt(dto.getTier()));

        RecResponseDto responseDto = RecResponseDto.builder()
                .rivalCnt(Integer.toString(tierCount))
                .rightCnt(dto.getRightCnt())
                .wrongCnt(dto.getWrongCnt())
                .userRank(dto.getRank())
                .result(proList.getResult())
                .build();

        return responseDto;
    }

    // 문제 기반 추천 서비스
    public RecResponseDto getListByProblem() throws IOException {
        Member currentMember2 = getCurrentMember();
        String bojName = currentMember2.getBojName();

        UserCrawlingDto dto = crawlingService.getUserInfo(bojName);

        WebClient webClient = WebClient.builder()
                .baseUrl(lambda2)
                .build();

        RecProResponseDto proList = webClient.post()
                .body(BodyInserters.fromValue(dto))
                .retrieve()
                .bodyToMono(RecProResponseDto.class)
                .block();

        Integer tierCount = tierCountRepository.findByTier(Integer.parseInt(dto.getTier()));

        RecResponseDto responseDto = RecResponseDto.builder()
                .rivalCnt(Integer.toString(tierCount))
                .rightCnt(dto.getRightCnt())
                .wrongCnt(dto.getWrongCnt())
                .userRank(dto.getRank())
                .result(proList.getResult())
                .build();

        return responseDto;
    }

    private Member getCurrentMember(){

        Member currentMember = SecurityUtil.getCurrentMember();
        Long currentMemberId = currentMember.getId();
        return memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ApiException(ErrorType._USER_NOT_FOUND_DB));
    }


}
