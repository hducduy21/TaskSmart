package com.tasksmart.sharedLibrary.repositories.httpClients;

import com.tasksmart.sharedLibrary.configs.interceptors.FeignRequestInterceptor;
import com.tasksmart.sharedLibrary.dtos.responses.TaskGenResponse;
import com.tasksmart.sharedLibrary.models.enums.EGender;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "py-helper", url = "http://localhost:8807")
public interface PyHelperClient {
    @GetMapping(value = "api/pyhelper/projects/{project_id}/generate-task", produces = MediaType.APPLICATION_JSON_VALUE)
    TaskGenResponse generateTask(@PathVariable String project_id);
}