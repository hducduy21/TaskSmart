package com.tasksmart.sharedLibrary.utils;

import com.tasksmart.sharedLibrary.exceptions.UnauthenticateException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUtils {

    public String getUserIdAuthenticated() {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(StringUtils.isBlank(userId)){
            throw new UnauthenticateException("Login required!");
        }

        return userId;
    }
}
