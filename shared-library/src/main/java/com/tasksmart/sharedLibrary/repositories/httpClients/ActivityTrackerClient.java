package com.tasksmart.sharedLibrary.repositories.httpClients;

import com.tasksmart.sharedLibrary.configs.interceptors.FeignRequestInterceptor;
import com.tasksmart.sharedLibrary.dtos.responses.ProjectAccessResponse;
import com.tasksmart.sharedLibrary.models.enums.EGender;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "activities", url = "http://localhost:8806", configuration = {FeignRequestInterceptor.class})
public interface ActivityTrackerClient {
    @GetMapping(value = "api/internal/activities/recent/projects", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ProjectAccessResponse> getProjectRecent();
}
