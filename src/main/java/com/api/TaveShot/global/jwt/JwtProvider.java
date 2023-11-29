package com.api.TaveShot.global.jwt;

import static com.api.TaveShot.global.constant.OauthConstant.ACCESS_TOKEN_VALID_TIME;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;

public class JwtProvider {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    public String generateAccessToken(String id) {
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

    private Claims createClaims(String id) {
        return Jwts.claims().setSubject(id);
    }

    private long calculateExpirationDate(Date now) {
        return now.getTime() + ACCESS_TOKEN_VALID_TIME;
    }

    private SecretKey generateKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

}
