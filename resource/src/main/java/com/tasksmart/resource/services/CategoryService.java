package com.tasksmart.resource.services;

import com.tasksmart.resource.dtos.requests.CategoryRequest;
import com.tasksmart.resource.dtos.responses.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAllCategories() ;
    List<CategoryResponse> searchCategories(String keyword);
    CategoryResponse getCategoryById(String id);
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    CategoryResponse updateCategory(String id, CategoryRequest categoryRequest);
    void deleteCategory(String id);


}
