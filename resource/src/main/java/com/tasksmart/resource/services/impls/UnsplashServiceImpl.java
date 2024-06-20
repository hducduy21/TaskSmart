package com.tasksmart.resource.services.impls;

import com.tasksmart.resource.services.UnsplashService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * UnsplashServiceImpl
 * Service class for Unsplash API
 * This class is responsible for making requests to Unsplash API
 * @author Duc Nguyen
 */

@Service
public class UnsplashServiceImpl implements UnsplashService {

    @Value("${unsplash.api.url}")
    private String apiUrl;

    @Value("${unsplash.access.key}")
    private String accessKey;

    private final WebClient webClient;


    public UnsplashServiceImpl(WebClient.Builder webClientBuilder,
                           @Value("${unsplash.api.url}") String apiUrl,
                           @Value("${unsplash.access.key}") String accessKey) {
        this.apiUrl = apiUrl;
        this.accessKey = accessKey;
        this.webClient = webClientBuilder
            .baseUrl(apiUrl)
            .defaultHeader("Authorization", "Client-ID " + accessKey)
            .build();
    }

    @Override
    public Mono<String> getPhotos(String query) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/search/photos")
                .queryParam("query", query)
                .build())
            .retrieve()
            .bodyToMono(String.class);

    }

    @Override
    public Mono<String> getRandomPhotos() {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/photos/random")
                .queryParam("count", 10)
                .build())
            .retrieve()
            .bodyToMono(String.class);
    }
}
