package com.tasksmart.sharedLibrary.repositories.httpClients.OAuth;


import com.tasksmart.sharedLibrary.dtos.responses.GitHubUserEmailResponse;
import com.tasksmart.sharedLibrary.dtos.responses.GitHubUserResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "github-api", url = "https://api.github.com")
public interface GitHubClient {
    @GetMapping(value = "/user")
    GitHubUserResponse getUserInfo(@RequestHeader("Authorization") String token);

    @GetMapping(value = "/user/emails")
    List<GitHubUserEmailResponse> getUserEmail(@RequestHeader("Authorization") String token);
}
