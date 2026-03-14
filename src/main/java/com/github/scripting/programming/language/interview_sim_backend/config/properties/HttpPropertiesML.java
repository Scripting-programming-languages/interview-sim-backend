package com.github.scripting.programming.language.interview_sim_backend.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("http.ml")
public record HttpPropertiesML(String baseUrl, long connectTimeout, long readTimeout) {
}
