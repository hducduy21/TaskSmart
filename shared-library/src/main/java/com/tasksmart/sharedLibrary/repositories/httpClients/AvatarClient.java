package com.tasksmart.sharedLibrary.repositories.httpClients;

import com.tasksmart.sharedLibrary.configs.interceptors.FeignRequestInterceptor;
import com.tasksmart.sharedLibrary.dtos.responses.UserGeneralResponse;
import com.tasksmart.sharedLibrary.models.enums.EGender;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "avatar", url = "http://localhost:8805", configuration = {FeignRequestInterceptor.class})
public interface AvatarClient {
    @GetMapping(value = "api/avatars/random", produces = MediaType.APPLICATION_JSON_VALUE)
    String getAvatarPathRandom(@RequestParam EGender gender);
}
