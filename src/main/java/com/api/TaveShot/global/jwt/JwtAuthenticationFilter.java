package com.api.TaveShot.global.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        if (isPublicUri(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null) {
            // ToDo Access Token 검증
            //  jwtProvider.isValidToken(authorizationHeader);

            filterChain.doFilter(request, response);
        }

    }

    private boolean isPublicUri(String requestURI) {
        return requestURI.equals("/auth/signup") ||
                requestURI.equals("/auth/login") ||
                requestURI.equals("/auth/logout") ||
                requestURI.equals("/auth/token") ||
                requestURI.startsWith("/oauth") ||
                requestURI.startsWith("/swagger-ui") ||
                requestURI.startsWith("/favicon.ico") ||
                requestURI.startsWith("/login");
    }
}
