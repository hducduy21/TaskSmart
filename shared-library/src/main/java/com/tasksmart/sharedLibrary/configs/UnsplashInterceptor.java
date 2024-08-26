package com.tasksmart.sharedLibrary.configs;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class UnsplashInterceptor implements RequestInterceptor {
    @Value("${unsplash.access.key}")
    private String accessKey;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", "Client-ID " + accessKey);
    }
}
