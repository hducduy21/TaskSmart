package com.tasksmart.sharedLibrary.services.impls;

import com.tasksmart.sharedLibrary.dtos.responses.UserGeneralResponse;
import com.tasksmart.sharedLibrary.repositories.httpClients.UserClient;
import com.tasksmart.sharedLibrary.services.UserClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserClientServiceImpl implements UserClientService {
    private final UserClient userClient;

    @Override
    public UserGeneralResponse getUserGeneralResponse(String userId) {
        return null;
    }
}
