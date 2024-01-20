package com.api.TaveShot.domain.recommend.controller;

import com.api.TaveShot.domain.post.post.dto.response.PostResponse;
import com.api.TaveShot.domain.recommend.dto.RecProResponseDto;
import com.api.TaveShot.domain.recommend.dto.RecResponseDto;
import com.api.TaveShot.domain.recommend.service.RecommendService;
import com.api.TaveShot.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/recommend")
public class RecommendController {

    private final RecommendService recommendService;

    @Operation(summary = "사용자 기반 문제 추천", description = "해당 유저가 푼 문제들을 바탕으로" +
            " 같은 티어의 사용자가 푼 문제들을 관련성이 높은 순으로 추천합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "추천 성공",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = PostResponse.class)))
    })
    @GetMapping("/rival")
    public SuccessResponse<RecResponseDto> getUserBasedProList() throws IOException {
        RecResponseDto response = recommendService.getListByUser();
        return new SuccessResponse<>(response);
    }

    @Operation(summary = "태그 관련 문제 추천", description = "원하는 문제와 태그가 같은 관련성이 높은 문제를 추천합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "추천 성공",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = PostResponse.class)))
    })
    @GetMapping("/problem")
    public SuccessResponse<RecResponseDto> getSolvedProList() throws IOException {
        RecResponseDto responseDto = recommendService.getListByProblem();
        return new SuccessResponse<>(responseDto);
    }
}
