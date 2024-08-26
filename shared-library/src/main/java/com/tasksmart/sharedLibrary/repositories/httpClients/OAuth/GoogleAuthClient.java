package com.tasksmart.sharedLibrary.repositories.httpClients.OAuth;


import com.tasksmart.sharedLibrary.dtos.request.ExchangeGoogleTokenRequest;
import com.tasksmart.sharedLibrary.dtos.responses.ExchangeGoogleTokenResponse;
import com.tasksmart.sharedLibrary.dtos.responses.GoogleUserResponse;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "google-identity", url = "https://oauth2.googleapis.com")
public interface GoogleAuthClient {
    @PostMapping(value = "/token", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ExchangeGoogleTokenResponse exchangeToken(@QueryMap ExchangeGoogleTokenRequest request);

    @GetMapping(value = "/tokeninfo", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    GoogleUserResponse tokenInfo(@RequestParam String id_token);
}
