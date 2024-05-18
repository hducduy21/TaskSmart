package com.example.user.services;

import com.example.user.dtos.request.UserSignInRequest;
import com.example.user.dtos.response.UserResponse;
import com.example.user.models.User;

public interface AuthService {
    /**
     * Authenticates a user based on the provided sign-in request.
     *
     * @param userSignInRequest The request object containing user sign-in credentials.
     * @return The authenticated user object if successful, otherwise null.
     */
    UserResponse login(UserSignInRequest userSignInRequest);
}
