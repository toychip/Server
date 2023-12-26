package com.api.TaveShot.domain.authorization.service;

import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.Member.domain.Tier;
import com.api.TaveShot.domain.authorization.dto.SolvedUserInfo;
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

        SolvedUserInfo solvedUserInfo = solvedAcApiService.getUserInfoFromSolvedAc(bojName);
        String gitHubNameBySolvedBio = solvedUserInfo.getBio();

        validateMatch(gitHubNameBySolvedBio);

        Integer bojTier = solvedUserInfo.getBojTier();

        // ToDo currentMember 티어 정보 추가
        Tier tierBySolvedApi = getTierBySolvedApi(bojTier);

        // ToDo 백준 닉네임 정보 추가
        changeBojName();
    }

    private Tier getTierBySolvedApi(Integer bojTier) {
        return Tier.fromBojTier(bojTier);
    }

    private void changeBojName() {

    }

    private void validateMatch(String githubNameBySolvedBio) {

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
