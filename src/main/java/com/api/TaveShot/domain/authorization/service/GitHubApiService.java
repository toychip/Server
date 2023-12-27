package com.api.TaveShot.domain.authorization.service;

import static com.api.TaveShot.global.constant.ApiConstant.GITHUB_URI;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.authorization.dto.GitHubRepositoryInfo;
import com.api.TaveShot.global.exception.ApiException;
import com.api.TaveShot.global.exception.ErrorType;
import com.api.TaveShot.global.util.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

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

        try {
            GitHubRepositoryInfo repositoryInfo = getGithubRepositoryInfo(gitLoginId);
            return extractDescription(repositoryInfo);
        } catch (WebClientResponseException wcrE) {
            throw new ApiException(ErrorType._WEB_CLIENT_ERROR);
        }
    }

    private GitHubRepositoryInfo getGithubRepositoryInfo(final String gitLoginId) {
        return webClient.get()
                .uri("/repos/" + gitLoginId + "/taveshot")
                .retrieve()
                .onStatus(BAD_REQUEST::equals, response ->
                        Mono.error(new ApiException(ErrorType._GITHUB_REPO_INVALID))) // 400 오류 처리
                .onStatus(NOT_FOUND::equals, response ->
                        Mono.error(new ApiException(ErrorType._GITHUB_REPO_NOT_FOUND))) // 404 오류 처리
                .onStatus(INTERNAL_SERVER_ERROR::equals, response ->
                        Mono.error(new ApiException(ErrorType._GITHUB_SERVER_ERROR))) // 500 서버 오류 처리

                .bodyToMono(GitHubRepositoryInfo.class)
                .block();
    }

    private String extractDescription(final GitHubRepositoryInfo repositoryInfo) {
        if (repositoryInfo == null || repositoryInfo.description() == null) {
            throw new ApiException(ErrorType._GITHUB_DESCRIPTION_NOT_FOUND);
        }
        return repositoryInfo.description();
    }

    private Member getCurrentMember() {
        return SecurityUtil.getCurrentMember();
    }
}
