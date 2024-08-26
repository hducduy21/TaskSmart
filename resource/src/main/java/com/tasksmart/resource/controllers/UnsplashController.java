package com.tasksmart.resource.controllers;

import com.tasksmart.sharedLibrary.dtos.messages.UnsplashResponse;
import com.tasksmart.resource.services.UnsplashService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is the controller for the Unsplash API.
 *
 * It handles the requests related to the Unsplash API.
 *
 * @author Duy Hoang
 */
@RestController
@RequestMapping("/api/unsplash")
@RequiredArgsConstructor
public class UnsplashController {

    private final UnsplashService unsplashService;

    /**
     * Get photos from Unsplash API.
     *
     * @param query The query to search for photos
     * @param page The page number
     * @param per_page The number of photos per page
     * @return The list of photos
     */
    @GetMapping("/photos")
    @ResponseStatus(HttpStatus.OK)
    public List<UnsplashResponse> getPhotos(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int per_page
    ) {
        return unsplashService.getPhotos(query, page, per_page);
    }

    /**
     * Get a photo by its id.
     *
     * @param unsplashId The id of the photo
     * @return The photo
     */
    @GetMapping("/photos/{unsplashId}")
    @ResponseStatus(HttpStatus.OK)
    public UnsplashResponse getPhotoById(
            @PathVariable String unsplashId
    ) {
        return unsplashService.getPhotoById(unsplashId);
    }
}
