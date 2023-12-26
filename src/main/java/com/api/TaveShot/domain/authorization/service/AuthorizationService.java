package com.api.TaveShot.domain.authorization.service;

import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.Member.domain.Tier;
import com.api.TaveShot.domain.Member.editor.MemberEditor;
import com.api.TaveShot.domain.authorization.dto.MemberResponse;
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

    public MemberResponse authorization() {
        String bojName = getBojNameFromGitHub();
        SolvedUserInfo solvedUserInfo = getSolvedUserInfo(bojName);

        validateMatch(solvedUserInfo);
        changeBojInfo(solvedUserInfo);

        return response();
    }

    private String getBojNameFromGitHub() {
        return gitHubApiService.getGithubRepoDescription();
    }

    private SolvedUserInfo getSolvedUserInfo(String bojName) {
        return solvedAcApiService.getUserInfoFromSolvedAc(bojName);
    }

    private void validateMatch(SolvedUserInfo solvedUserInfo) {
        String gitHubNameBySolvedBio = solvedUserInfo.getBio();

        Member currentMember = getCurrentMember();
        String gitLoginId = currentMember.getGitLoginId();

        if (!gitHubNameBySolvedBio.equals(gitLoginId)) {
            throw new ApiException(ErrorType._GITHUB_NAME_NOT_MATCH);
        }
    }

    private Member getCurrentMember() {
        return SecurityUtil.getCurrentMember();
    }

    private void changeBojInfo(SolvedUserInfo solvedUserInfo) {
        Integer bojTier = solvedUserInfo.getBojTier();
        Tier tier = calculateTier(bojTier);

        String bojName = solvedUserInfo.getBio();

        MemberEditor memberEditor = getMemberEditor(tier, bojName);

        Member member = getCurrentMember();
        member.changeBojInfo(memberEditor);
    }

    private Tier calculateTier(Integer bojTier) {
        return Tier.fromBojTier(bojTier);
    }

    private MemberEditor getMemberEditor(final Tier tier, final String bojName) {
        Member member = getCurrentMember();
        MemberEditor.MemberEditorBuilder editorBuilder = member.toEditor();
        MemberEditor memberEditor = editorBuilder
                .bojName(bojName)
                .tier(tier)
                .build();
        return memberEditor;
    }

    private MemberResponse response() {
        Member currentMember = getCurrentMember();
        String gitLoginId = currentMember.getGitLoginId();
        Tier tier = currentMember.getTier();
        String bojName = currentMember.getBojName();

        return MemberResponse.builder()
                .gitLoginId(gitLoginId)
                .tier(tier)
                .bojName(bojName)
                .build();
    }

}
