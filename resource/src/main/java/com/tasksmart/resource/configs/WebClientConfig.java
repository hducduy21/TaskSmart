package com.tasksmart.resource.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${unsplash.api.url}")
    private String apiUrl;

    @Value("${unsplash.access.key}")
    private String accessKey;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("Authorization", "Client-ID " + accessKey)
                .build();
    }
}
