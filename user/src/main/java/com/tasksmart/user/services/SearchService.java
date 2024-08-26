package com.tasksmart.user.services;

import com.tasksmart.sharedLibrary.dtos.responses.SearchAllResponse;

/**
 * Service interface for search-related operations.
 * This interface provides methods for searching
 *
 * @author Duy Hoang
 */
public interface SearchService {
    SearchAllResponse searchAll(String query);
}
