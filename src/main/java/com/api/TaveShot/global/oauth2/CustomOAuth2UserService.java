package com.api.TaveShot.global.oauth2;

import com.api.TaveShot.domain.Member.Member;
import com.api.TaveShot.domain.Member.MemberRepository;
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
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("-------------- 갖고 온 정보 -------------- getAttributes : {} ", oAuth2User.getAuthorities());

        Map<String, Object> attributes = oAuth2User.getAttributes();
        Member member = processOAuthUser(attributes);
        return createCustomOauth2User(member, oAuth2User);
    }

    private Member processOAuthUser(Map<String, Object> attributes) {
        String gitLoginId = (String) attributes.get("login");
        Long gitId = ((Integer) attributes.get("id")).longValue();
        String gitEmail = (String) attributes.get("email");
        String gitName = (String) attributes.get("name");

        return memberRepository.findByGitId(gitId)
                .orElseGet(() -> registerNewMember(gitId, gitLoginId, gitEmail, gitName));
    }

    private Member registerNewMember(Long gitId, String gitLoginId, String gitEmail, String gitName) {
        Member newMember = Member.builder()
                .gitId(gitId)
                .gitLoginId(gitLoginId)
                .gitEmail(gitEmail)
                .gitName(gitName)
                .build();

        return memberRepository.save(newMember);
    }

    private CustomOauth2User createCustomOauth2User(Member member, OAuth2User oAuth2User) {
        return CustomOauth2User.builder()
                .member(member)
                .attributes(oAuth2User.getAttributes())
                .build();
    }
}
