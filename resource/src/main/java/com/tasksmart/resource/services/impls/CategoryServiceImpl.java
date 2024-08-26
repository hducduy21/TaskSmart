package com.tasksmart.resource.services.impls;

import com.tasksmart.resource.dtos.requests.CategoryRequest;
import com.tasksmart.resource.dtos.responses.CategoryResponse;
import com.tasksmart.resource.models.Category;
import com.tasksmart.resource.repositories.CategoryRepository;
import com.tasksmart.resource.services.CategoryService;
import com.tasksmart.sharedLibrary.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CategoryResponse> getAllCategories(){
        List<Category> categories = categoryRepository.findAllByActiveTrue();
        return categories.stream().map(this::convertToCategoryResponse).toList();
    }

    @Override
    public List<CategoryResponse> searchCategories(String keyword) {
        return categoryRepository.findByNameContaining(keyword).stream()
                .map(this::convertToCategoryResponse)
                .toList();
    }

    @Override
    public CategoryResponse getCategoryById(String id){
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Category not found!")
        );
        return convertToCategoryResponse(category);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest){
        Category category = Category.builder()
                .name(categoryRequest.getName())
                .build();
        category = categoryRepository.save(category);
        return convertToCategoryResponse(category);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public CategoryResponse updateCategory(String id, CategoryRequest categoryRequest){
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Category not found!")
        );
        category.setName(categoryRequest.getName());
        category = categoryRepository.save(category);
        return convertToCategoryResponse(category);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void deleteCategory(String id){
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Category not found!")
        );
        category.setActive(false);
        categoryRepository.save(category);
    }

    private CategoryResponse convertToCategoryResponse(Category category){
       return modelMapper.map(category, CategoryResponse.class);
    }
}
