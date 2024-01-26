package com.api.TaveShot.domain.recommend.controller;

import com.api.TaveShot.domain.recommend.dto.RecResponseDto;
import com.api.TaveShot.domain.recommend.service.RecommendService;
import com.api.TaveShot.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/recommend")
public class RecommendController {

    private final RecommendService recommendService;

    @Operation(summary = "사용자 기반 문제 추천", description = "해당 유저가 푼 문제들을 바탕으로" +
            " 같은 티어의 사용자가 푼 문제들을 관련성이 높은 순으로 추천합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "추천 성공")
    })
    @GetMapping("/rival")
    public SuccessResponse<RecResponseDto> getUserBasedProList() throws IOException {
        RecResponseDto response = recommendService.getListByUser();
        return new SuccessResponse<>(response);
    }

    @Operation(summary = "태그 관련 문제 추천", description = "원하는 문제와 태그가 같은 관련성이 높은 문제를 추천합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "추천 성공")
    })
    @GetMapping("/problem")

    public SuccessResponse<RecResponseDto> getSolvedProList(@RequestParam(value = "solvedRecentId") String solvedNumber)
            throws IOException {
        RecResponseDto responseDto = recommendService.getListByProblem(solvedNumber);
        return new SuccessResponse<>(responseDto);
    }
}
