package com.example.user.configs;

import org.springframework.stereotype.Component;

/**
 * Component class for storing application constants.
 * This class contains various constants used throughout the application.
 *
 * @author Duy Hoang
 */
@Component
public class AppConstant {

    /** Prefix for API URLs. */
    public static final String URL_PREFIX = "/api";

    /** URL path for user-related API endpoints. */
    public static final String URL_USER = "/users";
}

