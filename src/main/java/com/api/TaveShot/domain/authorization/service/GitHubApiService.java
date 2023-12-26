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
public class GitHubApiService {
    private final WebClient webClient;

    public GitHubApiService() {
        webClient = WebClient.builder()
                .baseUrl(GITHUB_URI)
                .build();
    }

    public String getGithubRepoDescription() {
        Member currentMember = getCurrentMember();
        String gitLoginId = currentMember.getGitLoginId();

        GitHubRepositoryInfo repositoryInfo = webClient.get()
                .uri("/repos/" + gitLoginId + "/taveshot")
                .retrieve()
                .bodyToMono(GitHubRepositoryInfo.class)
                .block();

        return description(repositoryInfo);
    }

    private String description(GitHubRepositoryInfo repositoryInfo) {
        if (repositoryInfo != null) {
            return repositoryInfo.description();
        }
        throw new ApiException(ErrorType._GITHUB_REPO_INVALID);
    }

    private Member getCurrentMember() {
        return SecurityUtil.getCurrentMember();
    }
}
