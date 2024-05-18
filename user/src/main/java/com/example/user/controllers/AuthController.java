package com.example.user.controllers;

import com.example.user.dtos.request.UserSignInRequest;
import com.example.user.dtos.response.UserResponse;
import com.example.user.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling authentication-related endpoints.
 * This class provides endpoints for user authentication.
 *
 * @author Duy Hoang
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/${url_base}")
public class AuthController {
    /** Service instance for handling authentication operations. */
    private final AuthService authService;

    /**
     * Handles the login endpoint.
     *
     * @param userSignInRequest The request object containing user sign-in credentials.
     * @return The authenticated user object.
     */
    @PostMapping("login")
    public UserResponse login(@Valid @RequestBody UserSignInRequest userSignInRequest){
        return authService.login(userSignInRequest);
    }
}
