package com.github.scripting.programming.language.interview_sim_backend.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("security.cors")
public record CorsProperties(String[] allowedOrigins) {
}
