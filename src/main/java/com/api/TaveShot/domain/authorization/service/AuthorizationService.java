package com.api.TaveShot.domain.authorization.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final AuthorizationService authorizationService;
    private final GitHubApiService gitHubApiService;

    public void authorizationSolvedApi() {
    }


}
