package com.tasksmart.resource.controllers;

import com.tasksmart.sharedLibrary.dtos.messages.UnsplashResponse;
import com.tasksmart.resource.services.UnsplashService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/unsplash")
@RequiredArgsConstructor
public class UnsplashController {

    private final UnsplashService unsplashService;

    @GetMapping("/photos")
    @ResponseStatus(HttpStatus.OK)
    public List<UnsplashResponse> getPhotos(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int per_page
    ) {
        return unsplashService.getPhotos(query, page, per_page);
    }

    @GetMapping("/photos/{unsplashId}")
    @ResponseStatus(HttpStatus.OK)
    public UnsplashResponse getPhotoById(
            @PathVariable String unsplashId
    ) {
        return unsplashService.getPhotoById(unsplashId);
    }
}
