package com.tasksmart.resource.services;

import com.tasksmart.sharedLibrary.dtos.messages.UnsplashResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UnsplashService {
    Mono<List<UnsplashResponse>> getPhotos(String query, int page, int per_page);

    Mono<UnsplashResponse> getPhotoById(String unsplashId);
}
