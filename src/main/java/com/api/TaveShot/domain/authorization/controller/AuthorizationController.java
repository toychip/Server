package com.api.TaveShot.domain.authorization.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import com.api.TaveShot.domain.authorization.dto.MemberResponse;
import com.api.TaveShot.domain.authorization.service.AuthorizationService;
import com.api.TaveShot.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authorization")
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @Operation(summary = "백준 사용자 인증 정보 조회", description = "현재 로그인한 사용자의 백준 사용자 이름, 티어 및 GitHub 로그인 ID를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증 정보 조회 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MemberResponse.class)))
    })
    @GetMapping
    public SuccessResponse<MemberResponse> authorizationSolvedApi() {
        MemberResponse authorization = authorizationService.authorization();
        return new SuccessResponse<>(authorization);
    }
}
