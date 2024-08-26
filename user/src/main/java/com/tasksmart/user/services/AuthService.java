package com.tasksmart.user.services;

import com.tasksmart.user.dtos.request.UserSignInRequest;
import com.tasksmart.user.dtos.response.AuthResponse;
import com.tasksmart.user.dtos.response.UserGeneralResponse;

/**
 * Service interface for authentication-related operations.
 * This interface provides methods for authenticating
 * users based on their credentials.
 *
 * @author Duy Hoang
 */
public interface AuthService {

    /**
     * Introspects the current user.
     *
     * @return The introspected user object.
     */
    UserGeneralResponse introspect();

    /**
     * Authenticates a user based on the provided sign-in request.
     *
     * @param userSignInRequest The request object containing user sign-in credentials.
     * @return The authenticated user object if successful, otherwise null.
     */
    AuthResponse login(UserSignInRequest userSignInRequest);

    /**
     * Authenticates a user based on the provided sign-in request.
     *
     * @param code The request object containing user sign-in credentials.
     * @return The authenticated user object if successful, otherwise null.
     */
    AuthResponse googleAuthenticate(String code);

    /**
     * Authenticates a user based on the provided sign-in request.
     *
     * @param code The request object containing user sign-in credentials.
     * @return The authenticated user object if successful, otherwise null.
     */
    AuthResponse githubAuthenticate(String code);

    /**
     * Refreshes the current user's access token.
     *
     * @param refresh The refresh token.
     * @return The refreshed access token.
     */
    AuthResponse refresh(String refresh);
}
