package com.tasksmart.resource.services.impls.clients;

import com.tasksmart.sharedLibrary.dtos.responses.ProjectTemplateResponse;
import com.tasksmart.sharedLibrary.dtos.responses.SearchAllResponse;
import com.tasksmart.sharedLibrary.repositories.httpClients.WorkSpaceClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WorkSpaceService {
    private final WorkSpaceClient workSpaceClient;

    @CircuitBreaker(name = "workspaceService", fallbackMethod = "getProjectTemplateFallback")
    public ProjectTemplateResponse getProjectTemplate(String id){
        return workSpaceClient.getProjectTemplate(id);
    }

    public ProjectTemplateResponse getProjectTemplateFallback(String id, Throwable throwable){
        return ProjectTemplateResponse.builder()
                .backgroundColor("JpTY4gUviJM")
                .id("id")
                .name("Somethings went wrong!")
                .description("Somethings went wrong! Pls try later.").build();
    }
}
