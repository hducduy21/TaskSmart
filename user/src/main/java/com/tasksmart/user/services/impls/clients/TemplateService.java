package com.tasksmart.user.services.impls.clients;

import com.tasksmart.sharedLibrary.dtos.responses.SearchAllResponse;
import com.tasksmart.sharedLibrary.repositories.httpClients.TemplateClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TemplateService {
    private final TemplateClient templateClient;

    @CircuitBreaker(name = "templateService", fallbackMethod = "getTemplateSearchFallback")
    public SearchAllResponse search(String query){
        return templateClient.search(query);
    }

    public SearchAllResponse getTemplateSearchFallback(String query, Throwable throwable){
        return SearchAllResponse.builder().build();
    }
}
