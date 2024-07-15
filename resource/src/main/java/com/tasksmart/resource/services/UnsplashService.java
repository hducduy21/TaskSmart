package com.tasksmart.resource.services;

import com.tasksmart.sharedLibrary.dtos.messages.UnsplashResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UnsplashService {
    List<UnsplashResponse> getPhotos(String query, int page, int per_page);

    UnsplashResponse getPhotoById(String unsplashId);
}
