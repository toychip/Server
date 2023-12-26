package com.api.TaveShot.domain.authorization.service;

import static com.api.TaveShot.global.constant.ApiConstant.GITHUB_URI;

import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.authorization.dto.GitHubRepositoryInfo;
import com.api.TaveShot.global.exception.ApiException;
import com.api.TaveShot.global.exception.ErrorType;
import com.api.TaveShot.global.util.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AuthorizationService {

    private final WebClient webClient;

    public AuthorizationService() {
        this.webClient = WebClient.builder()
                .baseUrl(GITHUB_URI)
                .build();
    }

    public void authorizationSolvedApi() {
    }

    public String getGithubRepoDescription() {
        Member currentMember = getCurrentMember();
        String gitLoginId = currentMember.getGitLoginId();

        GitHubRepositoryInfo repositoryInfo = webClient.get()
                .uri("/repos/" + gitLoginId + "/test")
                .retrieve()
                .bodyToMono(GitHubRepositoryInfo.class)
                .block();

        if (repositoryInfo != null) {
            return repositoryInfo.description();
        } else {
            throw new ApiException(ErrorType._GITHUB_API_REPO_INVALID);
        }
    }

    public Member getCurrentMember() {
        return SecurityUtil.getCurrentMember();
    }
}
