package com.api.TaveShot.domain.recommend.service;

import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.Member.repository.MemberRepository;
import com.api.TaveShot.domain.recommend.domain.ProblemElement;
import com.api.TaveShot.domain.recommend.dto.RecProDetailResponseDto;
import com.api.TaveShot.domain.recommend.dto.RecProRequestDto;
import com.api.TaveShot.domain.recommend.dto.RecProResponseDto;
import com.api.TaveShot.domain.recommend.dto.RecResponseDto;
import com.api.TaveShot.domain.recommend.dto.UserCrawlingDto;
import com.api.TaveShot.domain.recommend.repository.ProblemElementRepository;
import com.api.TaveShot.domain.recommend.repository.TierCountRepository;
import com.api.TaveShot.global.exception.ApiException;
import com.api.TaveShot.global.exception.ErrorType;
import com.api.TaveShot.global.util.SecurityUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecommendService {

    private final CrawlingService crawlingService;
    private final MemberRepository memberRepository;
    private final TierCountRepository tierCountRepository;
    private final ProblemElementRepository problemElementRepository;

    @Value("${lambda.secret.url1}")
    private String lambda1;

    @Value("${lambda.secret.url2}")
    private String lambda2;

    // 사용자 기반 추천 서비스
    public RecResponseDto getListByUser() throws IOException {
        UserCrawlingDto dto = getUserInfo();
        WebClient webClient = WebClient.builder()
                .baseUrl(lambda1)
                .build();

        RecProResponseDto proList = webClient.post()
                .body(BodyInserters.fromValue(dto))
                .retrieve()
                .bodyToMono(RecProResponseDto.class)
                .block();

        Integer tierCount;
        if (dto.getTier().equals("0")) {
            tierCount = 0;
        } else {
            tierCount = tierCountRepository.findByTier(Integer.parseInt(dto.getTier()));
        }
        log.info("tier:{}", tierCount);

        List<RecProDetailResponseDto> proDetailResponseDtos = getProblemDetail(proList);

        RecResponseDto responseDto = RecResponseDto.builder()
                .rivalCnt(Integer.toString(tierCount))
                .rightCnt(dto.getRightCnt())
                .wrongCnt(dto.getWrongCnt())
                .userRank(dto.getRank())
                .result(proDetailResponseDtos)
                .build();

        return responseDto;
    }

    // 문제 기반 추천 서비스
    public RecResponseDto getListByProblem(Long solvedRecentId) throws IOException {

        UserCrawlingDto dto = getUserInfo();

        WebClient webClient = WebClient.builder()
                .baseUrl(lambda2)
                .build();

        problemElementRepository.findByProblemId(solvedRecentId)
                .orElseThrow(() -> new ApiException(ErrorType._PROBLEM_NOT_FOUND));

        RecProRequestDto requestDto = RecProRequestDto.builder()
                .solvedRecentId(solvedRecentId)
                .build();

        RecProResponseDto proList = webClient.post()
                .body(BodyInserters.fromValue(requestDto))
                .retrieve()
                .bodyToMono(RecProResponseDto.class)
                .block();

        Integer tierCount;
        if (dto.getTier().equals("0")) {
            tierCount = 0;
        } else {
            tierCount = tierCountRepository.findByTier(Integer.parseInt(dto.getTier()));
        }
        log.info("tier:{}", tierCount);

        List<RecProDetailResponseDto> proDetailResponseDtos = getProblemDetail(proList);

        RecResponseDto responseDto = RecResponseDto.builder()
                .rivalCnt(Integer.toString(tierCount))
                .rightCnt(dto.getRightCnt())
                .wrongCnt(dto.getWrongCnt())
                .userRank(dto.getRank())
                .result(proDetailResponseDtos)
                .build();

        return responseDto;
    }

    public UserCrawlingDto getUserInfo() throws IOException {
        Member currentMember2 = getCurrentMember();
        String bojName = currentMember2.getBojName();
//        String bojName = "cucubab";

        UserCrawlingDto dto = crawlingService.getUserInfo(bojName);

        return dto;
    }

    private Member getCurrentMember() {
        Member currentMember = SecurityUtil.getCurrentMember();
        Long currentMemberId = currentMember.getId();
        return memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ApiException(ErrorType._USER_NOT_FOUND_DB));
    }

    private String getTierName(Integer bojLevel) {
        String[] tiers = {"BRONZE", "SILVER", "GOLD", "PLATINUM", "DIAMOND", "RUBY", "MASTER"};
        if (bojLevel <= 5) {
            return tiers[0];
        } else if (bojLevel <= 10) {
            return tiers[1];
        } else if (bojLevel <= 15) {
            return tiers[2];
        } else if (bojLevel <= 20) {
            return tiers[3];
        } else if (bojLevel <= 25) {
            return tiers[4];
        } else if (bojLevel <= 30) {
            return tiers[5];
        }

        return tiers[6];
    }

    private List<String> extractWords(String bojTags) {
        List<String> tags = new ArrayList<>();

        Pattern pattern = Pattern.compile("\\b\\w+\\b");
        Matcher matcher = pattern.matcher(bojTags);

        while (matcher.find()) {
            tags.add(matcher.group());
        }

        return tags;
    }

    private List<RecProDetailResponseDto> getProblemDetail(RecProResponseDto proList) {
        List<String> result = proList.getResult();
        List<RecProDetailResponseDto> proDetailResponseDto = new ArrayList<>();

        // 문제 세부 정보 찾기
        for (int i = 0; i < 15; i++) {
            Integer num = Integer.parseInt(result.get(i));
            log.info("num:{}", num);
            try {
                ProblemElement problemElement = problemElementRepository.findByProblemId(
                                Long.parseLong(String.valueOf(num)))
                        .orElseThrow(() -> new ApiException(ErrorType._PROBLEM_NOT_FOUND));
                log.info("{}, {}, {}", problemElement.getProblemId(), problemElement.getBojLevel(),
                        problemElement.getBojKey());
                String tierName = getTierName(problemElement.getBojLevel());
                List<String> tags = extractWords(problemElement.getBojKey());
                RecProDetailResponseDto detailResponseDto = RecProDetailResponseDto.builder()
                        .title(problemElement.getTitle())
                        .problemId(String.valueOf(num))
                        .tier(tierName)
                        .bojTag(tags)
                        .build();
                proDetailResponseDto.add(detailResponseDto);
            } catch (Exception e) {
                continue;
            }

        }

        return proDetailResponseDto;
    }

}
