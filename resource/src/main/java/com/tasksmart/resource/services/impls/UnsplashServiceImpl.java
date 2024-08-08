package com.tasksmart.resource.services.impls;

import com.tasksmart.sharedLibrary.dtos.messages.UnsplashResponse;
import com.tasksmart.resource.services.UnsplashService;
import com.tasksmart.sharedLibrary.dtos.responses.UnsplashPagination;
import com.tasksmart.sharedLibrary.exceptions.BadRequest;
import com.tasksmart.sharedLibrary.repositories.httpClients.UnsplashClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private final UnsplashClient unsplashClient;

    @Override
//    @CircuitBreaker(name = "unsplash", fallbackMethod = "fallbackGetPhotos")
    public List<UnsplashResponse> getPhotos(String query, int page, int per_page) {
        if(StringUtils.isBlank(query)){
            return this.getRandomPhotos(page*per_page);
        }
        UnsplashPagination unsplashPagination = unsplashClient.unsplashSearch(query, page, per_page);
        return unsplashPagination.getResults();

    }

//    private List<UnsplashResponse> fallbackGetPhotos(
//            String query,
//            int page,
//            int per_page,
//            Throwable throwable
//    ) {
//        return new ArrayList<>();
//    }

    @Override
    public UnsplashResponse getPhotoById(String unsplashId) {
        if(StringUtils.isBlank(unsplashId)){
            throw new BadRequest("Unsplash ID is required");
        }
        return unsplashClient.getUnsplashPhotoById(unsplashId);
    }

    public List<UnsplashResponse> getRandomPhotos(int count) {
        return unsplashClient.randomPhoto(count);
    }
}
