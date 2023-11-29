package com.api.TaveShot.global.oauth2;

import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.Member.dto.response.AuthResponse;
import com.api.TaveShot.domain.Member.repository.MemberRepository;
import com.api.TaveShot.global.jwt.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomOAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final String REDIRECT_URL = "http://localhost:5173";
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        CustomOauth2User oauth2User = (CustomOauth2User) authentication.getPrincipal();

        Map<String, Object> userInfo = oauth2User.getAttributes();
        GithubUserInfo githubUserInfo = GithubUserInfo.builder()
                .userInfo(userInfo)
                .build();

        if(response.isCommitted()) {
            log.debug("------------------ Response 전송 완료");
        }

        String profileImageUrl = githubUserInfo.getProfileImageUrl();
        String loginId = githubUserInfo.getLoginId();
        String name = githubUserInfo.getName();
        Long gitId = githubUserInfo.getId();

        log.info("------------------ "
                + "소셜 로그인 성공: " + loginId
                + "프로필 이미지: " + profileImageUrl
                + "이름" + name);

        Member loginMember = memberRepository.findByGitId(gitId).orElseThrow(() -> new RuntimeException(""));
        String loginMemberId = String.valueOf(loginMember.getId());

        String accessToken = jwtProvider.generateAccessToken(loginMemberId);
        // 어세스 토큰은 헤더에 담아서 응답으로 보냄
        response.setHeader("Authorization", accessToken);

        AuthResponse authResponse = AuthResponse.builder()
                .memberId(loginMember.getId())
                .gitLoginId(loginId)
                .gitProfileImageUrl(profileImageUrl)
                .build();


        // ---------------------------------------------------------------------
        // ToDo 아래는 임시 데이터, front와 협의 후 수정
        String encodedMemberId = URLEncoder.encode(String.valueOf(authResponse.memberId()), StandardCharsets.UTF_8);
        String encodedLoginId = URLEncoder.encode(authResponse.gitLoginId(), StandardCharsets.UTF_8);
        String encodedGitProfileImageUrl = URLEncoder.encode(authResponse.gitProfileImageUrl(), StandardCharsets.UTF_8);

        // 프론트엔드 페이지로 토큰과 함께 리다이렉트
        String frontendRedirectUrl = String.format(
                "%s/oauth2/github/code?token=%s&memberId=%s&gitLoginId=%s&profileImgUrl=%s",
                REDIRECT_URL, accessToken, encodedMemberId, encodedLoginId, encodedGitProfileImageUrl);
        response.sendRedirect(frontendRedirectUrl);
    }

}
