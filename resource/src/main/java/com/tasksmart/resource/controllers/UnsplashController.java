package com.tasksmart.resource.controllers;

import com.tasksmart.resource.services.UnsplashService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/unsplash")
@RequiredArgsConstructor
public class UnsplashController {

    private final UnsplashService unsplashService;

    @GetMapping("/photos")
    @ResponseStatus(HttpStatus.OK)
    public Mono<String> getPhotos(@RequestParam(required = false)  String query) {
        if (query == null || query.isEmpty()) {
            return unsplashService.getRandomPhotos();
        }
        return unsplashService.getPhotos(query);
    }
}
