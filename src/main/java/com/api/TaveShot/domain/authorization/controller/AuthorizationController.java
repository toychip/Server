package com.api.TaveShot.domain.authorization.controller;

import com.api.TaveShot.domain.authorization.dto.MemberResponse;
import com.api.TaveShot.domain.authorization.service.AuthorizationService;
import com.api.TaveShot.global.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @GetMapping
    public SuccessResponse<MemberResponse> authorizationSolvedApi() {
        MemberResponse authorization = authorizationService.authorization();
        return new SuccessResponse<>(authorization);
    }
}
