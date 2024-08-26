package com.tasksmart.workspace.services.impls.clients;

import com.tasksmart.sharedLibrary.dtos.responses.CategoryResponse;
import com.tasksmart.sharedLibrary.repositories.httpClients.CategoryClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * CategoryService class for managing Category operations.
 * This class is used to manage the operations of Category.
 *
 */
@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryClient categoryClient;

    /**
     * Get category by id
     * @param categoryId
     * @return CategoryResponse
     */
    @CircuitBreaker(name = "resourceService", fallbackMethod = "getCategoryFallback")
    public CategoryResponse getCategory(String categoryId){
        return categoryClient.getCategory(categoryId);
    }

    /**
     * Fallback method for getCategory
     * @param categoryId
     * @param throwable
     * @return CategoryResponse
     */
    public CategoryResponse getCategoryFallback(String categoryId, Throwable throwable){
        return CategoryResponse.builder().id(categoryId).name("...").build();
    }
}
