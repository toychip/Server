package com.api.TaveShot.global.oauth2;

import static com.api.TaveShot.global.constant.OauthConstant.EMAIL_PATTERN;
import static com.api.TaveShot.global.constant.OauthConstant.ID_PATTERN;
import static com.api.TaveShot.global.constant.OauthConstant.LOGIN_PATTERN;
import static com.api.TaveShot.global.constant.OauthConstant.PROFILE_IMAGE_URL_PATTERN;

import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.Member.repository.MemberRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(final OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("-------------- 갖고 온 정보 -------------- getAttributes : {} ", oAuth2User.getAuthorities());

        Map<String, Object> userInfo = oAuth2User.getAttributes();
        Member member = processOAuthUser(userInfo);
        return createCustomOauth2User(member, userInfo);
    }

    private Member processOAuthUser(final Map<String, Object> userInfo) {
        String gitLoginId = (String) userInfo.get(LOGIN_PATTERN);
        Long gitId = ((Integer) userInfo.get(ID_PATTERN)).longValue();
        String gitEmail = (String) userInfo.get(EMAIL_PATTERN);
        String profileImageUrl = (String) userInfo.get(PROFILE_IMAGE_URL_PATTERN);

        return memberRepository.findByGitId(gitId)
                .orElseGet(() -> registerNewMember(gitId, gitLoginId, gitEmail, profileImageUrl));
    }

    private Member registerNewMember(final Long gitId, final String gitLoginId,
                                     final String gitEmail, final String profileImageUrl) {
        Member newMember = Member.builder()
                .gitId(gitId)
                .gitLoginId(gitLoginId)
                .gitEmail(gitEmail)
                .profileImageUrl(profileImageUrl)
                .build();

        return memberRepository.save(newMember);
    }

    private CustomOauth2User createCustomOauth2User(final Member member, final Map<String, Object> userInfo) {
        GithubUserInfo githubUserInfo = generateGithubInfo(userInfo);

        return CustomOauth2User.builder()
                .member(member)
                .githubUserInfo(githubUserInfo)
                .build();
    }

    private GithubUserInfo generateGithubInfo(final Map<String, Object> userInfo) {
        return GithubUserInfo.builder()
                .userInfo(userInfo)
                .build();
    }
}
