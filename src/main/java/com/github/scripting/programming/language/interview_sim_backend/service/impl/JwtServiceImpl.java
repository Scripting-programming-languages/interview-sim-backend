package com.github.scripting.programming.language.interview_sim_backend.service.impl;

import com.github.scripting.programming.language.interview_sim_backend.config.properties.JwtProperties;
import com.github.scripting.programming.language.interview_sim_backend.entity.User;
import com.github.scripting.programming.language.interview_sim_backend.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
@EnableConfigurationProperties(JwtProperties.class)
public class JwtServiceImpl implements JwtService {
    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;

    public JwtServiceImpl(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;

        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.secretKey());
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public boolean isValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isAccessTokenExpired(token);
    }

    @Override
    public boolean isValidRefresh(String token, UserDetails user) {
        // TODO: add refresh
        return false;
    }

    @Override
    public String generateAccessToken(User user) {
        return generateToken(user, jwtProperties.accessTokenExpiration());
    }

    private String generateToken(User user, Long tokenExpiration) {
        long currentTimeMillis = System.currentTimeMillis();
        JwtBuilder builder = Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date(currentTimeMillis))
                .expiration(new Date(currentTimeMillis + tokenExpiration))
                .signWith(secretKey);

        return builder.compact();
    }

    @Override
    public String generateRefreshToken(User user) {
        return generateToken(user, jwtProperties.refreshTokenExpiration());
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isAccessTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
