package com.api.TaveShot.domain.recommend.controller;

import com.api.TaveShot.domain.recommend.dto.RecProResponseDto;
import com.api.TaveShot.domain.recommend.service.RecommendService;
import com.api.TaveShot.global.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("/problem")
    public SuccessResponse getSolvedProList() throws IOException {
        RecProResponseDto response = recommendService.getListByProblem();
        return new SuccessResponse<>(response);
    }

//    @GetMapping("/rival")
//    public SuccessResponse
}
