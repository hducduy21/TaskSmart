package com.tasksmart.sharedLibrary.repositories.httpClients;

import com.tasksmart.sharedLibrary.configs.interceptors.FeignRequestInterceptor;
import com.tasksmart.sharedLibrary.dtos.request.ProjectTemplateRequest;
import com.tasksmart.sharedLibrary.dtos.responses.ProjectTemplateResponse;
import com.tasksmart.sharedLibrary.dtos.responses.SearchAllResponse;
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

    @PostMapping(value = "api/internal/projects/apply", produces = MediaType.APPLICATION_JSON_VALUE)
    String applyTemplate(@RequestParam String projectId, @RequestParam String workspaceId, @RequestParam String projectName);

    @PostMapping(value = "api/internal/projects", produces = MediaType.APPLICATION_JSON_VALUE)
    ProjectTemplateResponse createProjectTemplate(@RequestBody ProjectTemplateRequest projectTemplateRequest);

    @PutMapping(value = "api/internal/projects", produces = MediaType.APPLICATION_JSON_VALUE)
    ProjectTemplateResponse updateProjectTemplate(@RequestBody ProjectTemplateRequest projectTemplateRequest);

    @GetMapping(value = "api/internal/projects/{projectId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ProjectTemplateResponse getProjectTemplate(@PathVariable String projectId);

    @GetMapping(value = "api/workspaces/search", produces = MediaType.APPLICATION_JSON_VALUE)
    SearchAllResponse search(@RequestParam String query);
}
