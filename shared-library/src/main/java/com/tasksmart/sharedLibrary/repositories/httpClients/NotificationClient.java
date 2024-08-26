package com.tasksmart.sharedLibrary.repositories.httpClients;

import com.tasksmart.sharedLibrary.configs.interceptors.FeignRequestInterceptor;
import com.tasksmart.sharedLibrary.dtos.responses.UserGeneralResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "notification", url = "http://localhost:8803",configuration = {FeignRequestInterceptor.class})
public interface NotificationClient {
    @GetMapping(value = "api/internal/verify", produces = MediaType.APPLICATION_JSON_VALUE)
    boolean verifyEmail(@RequestParam String email, @RequestParam String code);
}
