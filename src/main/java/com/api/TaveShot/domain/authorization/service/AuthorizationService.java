package com.api.TaveShot.domain.authorization.service;

import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.Member.domain.Tier;
import com.api.TaveShot.domain.Member.editor.MemberEditor;
import com.api.TaveShot.domain.Member.repository.MemberRepository;
import com.api.TaveShot.domain.authorization.dto.MemberResponse;
import com.api.TaveShot.domain.authorization.dto.SolvedUserInfo;
import com.api.TaveShot.global.exception.ApiException;
import com.api.TaveShot.global.exception.ErrorType;
import com.api.TaveShot.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorizationService {

    private final SolvedAcApiService solvedAcApiService;
    private final GitHubApiService gitHubApiService;
    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponse authorization() {
        String bojName = getBojNameFromGitHub();
        log.info("------------------1-------------------"+bojName);
        SolvedUserInfo solvedUserInfo = getSolvedUserInfo(bojName);

        validateMatch(solvedUserInfo);
        log.info("------------------2-------------------"+solvedUserInfo);

        changeBojInfo(solvedUserInfo.getBojTier(), bojName);
        log.info("------------------3-------------------"+solvedUserInfo);

        return response();
    }

    private String getBojNameFromGitHub() {
        return gitHubApiService.getGithubRepoDescription();
    }

    private SolvedUserInfo getSolvedUserInfo(final String bojName) {
        return solvedAcApiService.getUserInfoFromSolvedAc(bojName);
    }

    private void validateMatch(final SolvedUserInfo solvedUserInfo) {
        String gitHubNameBySolvedBio = solvedUserInfo.getBio();

        Member currentMember = getCurrentMember();
        String gitLoginId = currentMember.getGitLoginId();

        if (!gitHubNameBySolvedBio.equals(gitLoginId)) {
            throw new ApiException(ErrorType._GITHUB_NAME_NOT_MATCH);
        }
    }

    private Member getCurrentMember() {
        Member currentMember = SecurityUtil.getCurrentMember();
        Long currentMemberId = currentMember.getId();
        return memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ApiException(ErrorType._USER_NOT_FOUND_DB));
    }

    private Tier calculateTier(Integer bojTier) {
        return Tier.fromBojTier(bojTier);
    }

    @Transactional
    public void changeBojInfo(final Integer bojTier, final String bojName) {
        Tier tier = calculateTier(bojTier);
        log.info("------------------2.2-------------------"+bojName);

        MemberEditor memberEditor = getMemberEditor(tier, bojName);

        Member member = getCurrentMember();
        member.changeBojInfo(memberEditor);
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
        log.info("------------------4-------------------"+bojName);
        return MemberResponse.builder()
                .gitLoginId(gitLoginId)
                .tier(tier)
                .bojName(bojName)
                .build();
    }

}
