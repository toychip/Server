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

    public String generateJwtToken(String id) {
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
    private Claims createClaims(String id) {
        return Jwts.claims().setSubject(id);
    }

    // JWT 만료 시간 계산
    private long calculateExpirationDate(Date now) {
        return now.getTime() + ACCESS_TOKEN_VALID_TIME;
    }

    private SecretKey generateKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    // 토큰의 유효성 검사
    public void isValidToken(String jwtToken) {
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

    public void getAuthenticationFromToken(String jwtToken) {

        Long userId = Long.valueOf(getUserIdFromToken(jwtToken));
        Member findMember = memberRepository.findById(userId).orElseThrow(() -> new RuntimeException("token 으로 Member를 찾을 수 없음"));
        String gitName = findMember.getGitName();

        log.info("-------------- getAuthenticationFromToken 어세스토큰: " + jwtToken);

        // JWT 토큰이 유효하면, 사용자 정보를 연결 세션에 추가
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(gitName, jwtToken, new ArrayList<>());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    }

    // 토큰에서 유저 아이디 얻기
    public String getUserIdFromToken(String jwtToken) {
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
