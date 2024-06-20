package com.tasksmart.resource.services;

import reactor.core.publisher.Mono;

public interface UnsplashService {
    Mono<String> getPhotos(String query);
    Mono<String> getRandomPhotos();
}
