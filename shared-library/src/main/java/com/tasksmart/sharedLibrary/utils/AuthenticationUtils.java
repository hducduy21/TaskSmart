package com.tasksmart.sharedLibrary.utils;

import com.tasksmart.sharedLibrary.exceptions.UnauthenticateException;
import com.tasksmart.sharedLibrary.models.UserDetail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUtils {

    public String getUserIdAuthenticated() {
        UserDetail user = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(StringUtils.isBlank(user.getUserId())){
            throw new UnauthenticateException("Login required!");
        }

        return user.getUserId();
    }

    public UserDetail getUserAuthenticated() {
        UserDetail user = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(StringUtils.isBlank(user.getUserId()) && StringUtils.isBlank(user.getEmail())){
            throw new UnauthenticateException("Please login and try again!");
        }
        return user;
    }

    public boolean isAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }
}
