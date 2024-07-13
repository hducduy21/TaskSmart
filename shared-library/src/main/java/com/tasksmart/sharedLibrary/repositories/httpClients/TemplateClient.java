package com.tasksmart.sharedLibrary.repositories.httpClients;

import com.tasksmart.sharedLibrary.configs.interceptors.FeignRequestInterceptor;
import com.tasksmart.sharedLibrary.dtos.responses.SearchAllResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "template", url = "http://localhost:8805", configuration = {FeignRequestInterceptor.class})
public interface TemplateClient {
    @GetMapping(value = "api/templates/search", produces = MediaType.APPLICATION_JSON_VALUE)
    SearchAllResponse search(@RequestParam String query);
}
