package com.tasksmart.sharedLibrary.repositories.httpClients;

import com.tasksmart.sharedLibrary.configs.interceptors.FeignRequestInterceptor;
import com.tasksmart.sharedLibrary.dtos.responses.UserGeneralResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "user", url = "http://localhost:8801", configuration = {FeignRequestInterceptor.class})
public interface UserClient {

    @GetMapping(value = "api/internal/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    UserGeneralResponse getUserGeneralResponse(@PathVariable String userId);

    @GetMapping(value = "api/internal/users/lists", produces = MediaType.APPLICATION_JSON_VALUE)
    List<UserGeneralResponse> getUsersGeneralResponse(@RequestBody List<String> userIds);
}
