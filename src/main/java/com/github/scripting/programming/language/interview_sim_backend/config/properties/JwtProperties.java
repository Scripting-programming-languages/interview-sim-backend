package com.github.scripting.programming.language.interview_sim_backend.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("security.jwt")
public record JwtProperties(String secretKey, Long accessTokenExpiration, Long refreshTokenExpiration) {
}
