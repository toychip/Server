package com.api.TaveShot.global.jwt;

import static com.api.TaveShot.global.constant.OauthConstant.ACCESS_TOKEN_VALID_TIME;

import com.api.TaveShot.domain.Member.domain.Member;
import com.api.TaveShot.domain.Member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtProvider {

    private final MemberRepository memberRepository;

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    public String generateJwtToken(final String id) {
        Claims claims = createClaims(id);
        Date now = new Date();
        long expiredDate = calculateExpirationDate(now);
        SecretKey secretKey = generateKey();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(expiredDate))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // JWT claims 생성
    private Claims createClaims(final String id) {
        return Jwts.claims().setSubject(id);
    }

    // JWT 만료 시간 계산
    private long calculateExpirationDate(final Date now) {
        return now.getTime() + ACCESS_TOKEN_VALID_TIME;
    }

    // Key 생성
    private SecretKey generateKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    // 토큰의 유효성 검사
    public void isValidToken(final String jwtToken) {
        try {
            SecretKey key = generateKey();
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwtToken);

        } catch (ExpiredJwtException e) { // 어세스 토큰 만료
            throw new IllegalArgumentException("Access Token expired");
        } catch (Exception e) {
            throw new IllegalArgumentException("User Not Authorized");
        }
    }

    // jwtToken 으로 Authentication 에 사용자 등록
    public void getAuthenticationFromToken(final String jwtToken) {

        log.info("-------------- getAuthenticationFromToken jwt token: " + jwtToken);
        String gitName = getGitName(jwtToken);
        registerAuthentication(jwtToken, gitName);

    }

    // token 으로부터 유저 정보 확인
    private String getGitName(final String jwtToken) {
        Long userId = Long.valueOf(getUserIdFromToken(jwtToken));
        Member findMember = memberRepository.findById(userId).orElseThrow(() -> new RuntimeException("token 으로 Member를 찾을 수 없음"));
        return findMember.getGitName();
    }

    private void registerAuthentication(final String jwtToken, final String gitName) {
        // JWT 토큰이 유효하면, 사용자 정보를 연결 세션에 추가
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(gitName, jwtToken, new ArrayList<>());

        // SecurityContextHolder 유저 등록
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    // 토큰에서 유저 아이디 얻기
    public String getUserIdFromToken(final String jwtToken) {
        SecretKey key = generateKey();

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();

        log.info("-------------- JwtProvider.getUserIdFromAccessToken: " + claims.getSubject());
        return claims.getSubject();
    }
}
