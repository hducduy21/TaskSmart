package com.tasksmart.user.services;

import com.tasksmart.user.dtos.request.UserSignInRequest;
import com.tasksmart.user.dtos.response.AuthResponse;
import com.tasksmart.user.dtos.response.UserGeneralResponse;

public interface AuthService {

    UserGeneralResponse introspect();
    /**
     * Authenticates a user based on the provided sign-in request.
     *
     * @param userSignInRequest The request object containing user sign-in credentials.
     * @return The authenticated user object if successful, otherwise null.
     */
    AuthResponse login(UserSignInRequest userSignInRequest);

    AuthResponse refresh(String refresh);
}
