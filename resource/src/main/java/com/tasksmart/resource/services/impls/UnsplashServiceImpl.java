package com.tasksmart.resource.services.impls;

import com.tasksmart.resource.dtos.responses.UnsplashPagination;
import com.tasksmart.sharedLibrary.dtos.responses.UnsplashResponse;
import com.tasksmart.resource.services.UnsplashService;
import com.tasksmart.sharedLibrary.exceptions.BadRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * UnsplashServiceImpl
 * Service class for Unsplash API
 * This class is responsible for making requests to Unsplash API
 * @author Duc Nguyen
 */
@RequiredArgsConstructor
@Service
public class UnsplashServiceImpl implements UnsplashService {

    private final WebClient webClient;

    @Override
    public Mono<List<UnsplashResponse>> getPhotos(String query, int page, int per_page) {
        if(StringUtils.isBlank(query)){
            return this.getRandomPhotos(page*per_page);
        }

        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search/photos")
                .queryParam("query", query)
                .queryParam("page", page)
                .queryParam("per_page", per_page)
                .build())
                .retrieve()
                .bodyToMono(UnsplashPagination.class)
                .map(UnsplashPagination::getResults);

    }

    @Override
    public Mono<UnsplashResponse> getPhotoById(String unsplashId) {
        if(StringUtils.isBlank(unsplashId)){
            throw new BadRequest("Unsplash ID is required");
        }
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/photos/"+unsplashId)
                        .build())
                .retrieve()
                .bodyToMono(UnsplashResponse.class);
    }

    public Mono<List<UnsplashResponse>> getRandomPhotos(int count) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/photos/random")
                        .queryParam("count", count)
                        .build())
                .retrieve()
                .bodyToFlux(UnsplashResponse.class).collectList();
    }
}
