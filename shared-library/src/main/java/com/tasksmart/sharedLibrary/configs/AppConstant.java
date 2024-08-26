package com.tasksmart.sharedLibrary.configs;

import org.springframework.stereotype.Component;

/**
 * Component class for storing application constants.
 * This class contains various constants used throughout the application.
 *
 * @author Duy Hoang
 */
@Component
public class AppConstant {
    public static final String Role_Admin = "ADMIN";
    public static final String Role_User = "USER";

    public static final String IMG_AVATAR_FOLDER = "resource/avatars";
    public static final String IMG_TEMPLATE_FOLDER = "resource/templates";
    public static final String IMG_USER_FOLDER = "user";
}

