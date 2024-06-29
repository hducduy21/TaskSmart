package com.tasksmart.sharedLibrary.repositories.httpClients;

import com.tasksmart.sharedLibrary.dtos.messages.UnsplashResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "unsplash", url = "http://localhost:8805")
public interface UnsplashClient {
    @GetMapping(value = "api/unsplash/photos/{unsplashId}", produces = MediaType.APPLICATION_JSON_VALUE)
    UnsplashResponse getUnsplashPhotoById(@PathVariable String unsplashId);
}
