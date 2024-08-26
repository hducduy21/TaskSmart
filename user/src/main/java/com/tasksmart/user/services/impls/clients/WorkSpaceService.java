package com.tasksmart.user.services.impls.clients;

import com.tasksmart.sharedLibrary.dtos.responses.SearchAllResponse;
import com.tasksmart.sharedLibrary.repositories.httpClients.WorkSpaceClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WorkSpaceService {
    private final WorkSpaceClient workSpaceClient;

    @CircuitBreaker(name = "workspaceService", fallbackMethod = "getWorkspaceSearchFallback")
    public SearchAllResponse search(String query){
        return workSpaceClient.search(query);
    }

    public SearchAllResponse getWorkspaceSearchFallback(String query, Throwable throwable){
        return SearchAllResponse.builder().build();
    }
}
