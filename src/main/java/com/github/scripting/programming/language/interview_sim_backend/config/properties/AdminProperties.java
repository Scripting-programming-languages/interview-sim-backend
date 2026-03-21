package com.github.scripting.programming.language.interview_sim_backend.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("security.admin")
public record AdminProperties(String username, String password) {
}
