package com.api.TaveShot.domain.search.controller;

import com.api.TaveShot.domain.post.post.dto.response.PostResponse;
import com.api.TaveShot.domain.search.dto.GoogleListResponseDto;
import com.api.TaveShot.domain.search.dto.GoogleResponseDto;
import com.api.TaveShot.domain.search.service.SearchService;
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

    @Operation(summary = "해답 블로그 반환", description = "원하는 문제와 언어를 검색하여 " +
            "백준 해답 블로그 리스트를 제공합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "해답 제시 성공",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = PostResponse.class)))
    })
    @GetMapping
    public SuccessResponse<GoogleListResponseDto> getList(@RequestParam String query){
        GoogleListResponseDto response = searchService.findBlog(query, 1);
        return new SuccessResponse<>(response);
    }

    @Operation(summary = "해답 블로그 새로고침 수행", description = "처음 반환한 해답 블로그 리스트 외에 더 보고 싶은 블로그가 있다면" +
            " 새로고침을 통해 다른 리스트 제공")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "새로고침 성공",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = PostResponse.class)))
    })
    @GetMapping("/refresh")
    public SuccessResponse<GoogleListResponseDto> getRefresh(@RequestParam String query, @RequestParam(value = "start") int index){
        GoogleListResponseDto response = searchService.findBlog(query, index);
        return new SuccessResponse<>(response);
    }

}
