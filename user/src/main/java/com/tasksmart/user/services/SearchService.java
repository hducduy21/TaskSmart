package com.tasksmart.user.services;

import com.tasksmart.sharedLibrary.dtos.responses.SearchAllResponse;

public interface SearchService {
    SearchAllResponse searchAll(String query);
}
