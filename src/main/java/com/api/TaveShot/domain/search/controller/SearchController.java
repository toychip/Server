package com.api.TaveShot.domain.search.controller;

import com.api.TaveShot.domain.search.dto.GoogleListResponseDto;
import com.api.TaveShot.domain.search.dto.GoogleResponseDto;
import com.api.TaveShot.domain.search.service.SearchService;
import com.api.TaveShot.global.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public SuccessResponse<GoogleListResponseDto> getList(@RequestParam String query){
        GoogleListResponseDto response = searchService.findBlog(query);
        return new SuccessResponse<>(response);
    }
}
