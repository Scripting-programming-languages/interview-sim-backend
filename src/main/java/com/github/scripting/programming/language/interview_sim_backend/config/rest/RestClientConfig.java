package com.github.scripting.programming.language.interview_sim_backend.config.rest;

import com.github.scripting.programming.language.interview_sim_backend.config.properties.HttpCommonProperties;
import com.github.scripting.programming.language.interview_sim_backend.config.properties.HttpPropertiesML;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
@EnableConfigurationProperties({HttpPropertiesML.class, HttpCommonProperties.class})
public class RestClientConfig {

    @Bean(name = "mlRestClient")
    public RestClient mlRestClient(HttpPropertiesML httpPropertiesML, CloseableHttpClient httpClient) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        factory.setConnectionRequestTimeout(Duration.ofMillis(httpPropertiesML.connectTimeout()));
        factory.setReadTimeout(Duration.ofMillis(httpPropertiesML.readTimeout()));

        return RestClient.builder()
                .requestFactory(factory)
                .baseUrl(httpPropertiesML.baseUrl())
                .build();
    }

    @Bean
    public CloseableHttpClient closeableHttpClient(HttpCommonProperties httpCommonProperties) {
        return HttpClients.custom()
                .setConnectionManager(PoolingHttpClientConnectionManagerBuilder.create()
                        .setMaxConnTotal(httpCommonProperties.maxConnectionTotal())      // Макс. всего соединений
                        .setMaxConnPerRoute(httpCommonProperties.maxConnectionPerRoute())    // Макс. соединений на один хост
                        .build())
                .build();
    }
}
