package com.tasksmart.user.controllers;

import com.tasksmart.user.dtos.request.RefreshRequest;
import com.tasksmart.user.dtos.request.UserSignInRequest;
import com.tasksmart.user.dtos.response.AuthResponse;
import com.tasksmart.user.dtos.response.UserGeneralResponse;
import com.tasksmart.user.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling authentication-related endpoints.
 * This class provides endpoints for user authentication.
 *
 * @author Duy Hoang
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/${url_base}/${url_auth}")
@Slf4j
public class AuthController {
    /** Service instance for handling authentication operations. */
    private final AuthService authService;

    @GetMapping("introspect")
    @ResponseStatus(HttpStatus.OK)
    public UserGeneralResponse introspect(){
        return authService.introspect();
    }

    /**
     * Handles the login endpoint.
     *
     * @param userSignInRequest The request object containing user sign-in credentials.
     * @return The authenticated user object.
     */
    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@Valid @RequestBody UserSignInRequest userSignInRequest){
        return authService.login(userSignInRequest);
    }

    /**
     * Receive the refresh endpoint.
     *
     * @param refreshRequest The request object containing user sign-in credentials.
     * @return The authenticated user object.
     */
    @PostMapping("refresh")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse refresh(@Valid @RequestBody RefreshRequest refreshRequest){
        System.out.println(refreshRequest.getRefresh());
        return authService.refresh(refreshRequest.getRefresh());
    }
}
