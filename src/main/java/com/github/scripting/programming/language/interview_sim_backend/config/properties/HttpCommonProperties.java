package com.github.scripting.programming.language.interview_sim_backend.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("http.common")
public record HttpCommonProperties(int maxConnectionTotal, int maxConnectionPerRoute) {
}
