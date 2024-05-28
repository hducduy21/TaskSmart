package com.tasksmart.sharedLibrary.services;

import com.tasksmart.sharedLibrary.dtos.responses.UserGeneralResponse;

public interface UserClientService {
    UserGeneralResponse getUserGeneralResponse(String userId);
}
