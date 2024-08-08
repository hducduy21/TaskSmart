package com.tasksmart.sharedLibrary.repositories.httpClients;

import com.tasksmart.sharedLibrary.configs.UnsplashInterceptor;
import com.tasksmart.sharedLibrary.dtos.messages.UnsplashResponse;
import com.tasksmart.sharedLibrary.dtos.responses.UnsplashPagination;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "unsplash", url = "https://api.unsplash.com", configuration = {UnsplashInterceptor.class})
public interface UnsplashClient {
    @GetMapping(value = "/photos/{unsplashId}", produces = MediaType.APPLICATION_JSON_VALUE)
    UnsplashResponse getUnsplashPhotoById(@PathVariable String unsplashId);

    @GetMapping(value = "/search/photos", produces = MediaType.APPLICATION_JSON_VALUE)
    UnsplashPagination unsplashSearch(@RequestParam("query") String query, @RequestParam("page") int page, @RequestParam("per_page") int per_page);

    @GetMapping(value = "/photos/random", produces = MediaType.APPLICATION_JSON_VALUE)
    List<UnsplashResponse> randomPhoto(@RequestParam("count") int count);
}
