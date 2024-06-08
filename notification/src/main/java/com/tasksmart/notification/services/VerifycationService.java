package com.tasksmart.notification.services;

import com.tasksmart.notification.models.TokenVerifycation;

public interface VerifycationService {
    boolean verify(String token);
    TokenVerifycation verifycationRequest();
}
