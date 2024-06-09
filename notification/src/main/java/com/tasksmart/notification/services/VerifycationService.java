package com.tasksmart.notification.services;

import com.tasksmart.notification.models.TokenVerifycation;

public interface VerifycationService {
    boolean verify( String email, String code);
    boolean verifyInternal( String email, String code);
    TokenVerifycation verifycationRequest(String email);
}
