package com.api.TaveShot.global.config;

import com.api.TaveShot.global.jwt.JwtAuthenticationFilter;
import com.api.TaveShot.global.oauth2.CustomOAuth2UserService;
import jakarta.servlet.Filter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
                    CorsConfiguration cors = new CorsConfiguration();
                    cors.setAllowedOrigins(List.of("*", "http://localhost:3000", "http://localhost:8080"));
                    cors.setAllowedMethods(List.of("GET", "POST", "PATCH", "DELETE"));
                    // cookie 비활성화
                    cors.setAllowCredentials(false);
                    // Authorization Header 노출
                    cors.addExposedHeader("Authorization");
                    return cors;
                }))
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(
                                        "/actuator/**"
                                        , "/swagger-ui/**"
                                        , "/api-docs/swagger-config"
                                        , "/members/login"
                                        , "/**"
                                ).permitAll()
                                .anyRequest().permitAll());
        http
                .oauth2Login()
                .authorizationEndpoint().baseUri("/oauth/authorize")
                .and()
                .redirectionEndpoint().baseUri("/oauth/callback")
                .and()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }


}
