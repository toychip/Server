package com.api.TaveShot.domain.authorization.service;

import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.global.exception.ApiException;
import com.api.TaveShot.global.exception.ErrorType;
import com.api.TaveShot.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final SolvedAcApiService solvedAcApiService;
    private final GitHubApiService gitHubApiService;

    public void authorization() {

        String bojName = gitHubApiService.getGithubRepoDescription();
        String githubNameBySolvedBio = solvedAcApiService.getUserInfoFromSolvedAc(bojName);

        validateSamePerson(githubNameBySolvedBio);

    }

    private void validateSamePerson(String githubNameBySolvedBio) {

        Member currentMember = getCurrentMember();
        String gitLoginId = currentMember.getGitLoginId();

        if (!githubNameBySolvedBio.equals(gitLoginId)) {
            throw new ApiException(ErrorType._GITHUB_NAME_NOT_MATCH);
        }
    }

    private Member getCurrentMember() {
        return SecurityUtil.getCurrentMember();
    }


}
