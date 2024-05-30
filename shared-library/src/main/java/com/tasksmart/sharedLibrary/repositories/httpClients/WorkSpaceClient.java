package com.tasksmart.sharedLibrary.repositories.httpClients;

import com.tasksmart.sharedLibrary.configs.interceptors.FeignRequestInterceptor;
import com.tasksmart.sharedLibrary.dtos.responses.WorkSpaceGeneralResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "workspace", url = "http://localhost:8802", configuration = {FeignRequestInterceptor.class})
public interface WorkSpaceClient {
    @GetMapping(value = "api/internal/workspaces/{workspaceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    WorkSpaceGeneralResponse getWorkspaceById(@PathVariable String workspaceId);

    @PostMapping(value = "api/internal/workspaces/personal", produces = MediaType.APPLICATION_JSON_VALUE)
    WorkSpaceGeneralResponse createPersonalWorkSpace(@RequestParam String userId, @RequestParam String name,@RequestParam String username);
}
