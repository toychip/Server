package com.api.TaveShot.global.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        if (isPublicUri(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && isBearer(authorizationHeader)) {
            String jwtToken = authorizationHeader.substring(7); // "Bearer " 이후의 문자열을 추출

            // token 단순 유효성 검증
            jwtProvider.isValidToken(jwtToken);

            // token을 활용하여 유저 정보 검증
            jwtProvider.getAuthenticationFromToken(jwtToken);
        }

        filterChain.doFilter(request, response);

    }

    private boolean isBearer(String authorizationHeader) {
        return authorizationHeader.startsWith("Bearer ");
    }

    private boolean isPublicUri(String requestURI) {
        return
                requestURI.startsWith("/oauth/**") ||
                requestURI.startsWith("/swagger-ui") ||
                requestURI.startsWith("/favicon.ico") ||
                requestURI.startsWith("/login/**");
    }
}
