package com.tasksmart.sharedLibrary.repositories.httpClients.OAuth;

import com.tasksmart.sharedLibrary.configs.interceptors.GitHubRequestInterceptor;
import com.tasksmart.sharedLibrary.dtos.request.ExchangeGitHubTokenRequest;
import com.tasksmart.sharedLibrary.dtos.request.ExchangeGoogleTokenRequest;
import com.tasksmart.sharedLibrary.dtos.responses.ExchangeGithubTokenResponse;
import feign.Headers;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "github-identity", url = "https://github.com", configuration = {GitHubRequestInterceptor.class})
public interface GitHubAuthClient {

    @PostMapping(value = "/login/oauth/access_token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @Headers("Accept: application/json")
    ExchangeGithubTokenResponse exchangeToken(@QueryMap ExchangeGitHubTokenRequest request);
}
