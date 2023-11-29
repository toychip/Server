package com.api.TaveShot.global.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomOAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final String REDIRECT_URL = "http://localhost:5173";

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

        log.info("------------------ "
                + "소셜 로그인 성공: " + loginId
                + "프로필 이미지: " + profileImageUrl
                + "이름" + name);
    }
}
