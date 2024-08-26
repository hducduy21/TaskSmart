package com.tasksmart.resource.controllers;

import com.tasksmart.resource.dtos.requests.CategoryRequest;
import com.tasksmart.resource.dtos.responses.CategoryResponse;
import com.tasksmart.resource.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is the controller for the categories.
 *
 * @author Duy Hoang
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/${url_base}/${url_category}")
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * Get all categories.
     *
     * @return The list of all categories
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponse> getAllCategories(){
        return categoryService.getAllCategories();
    }

    /**
     * Get a category by its id.
     *
     * @param id The id of the category
     * @return The category
     */
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponse getCategoryById(@PathVariable String id){
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponse> searchCategories(@RequestParam String keyword){
        return categoryService.searchCategories(keyword);
    }

    /**
     * Create a new category.
     *
     * @param categoryRequest The request to create a new category
     * @return The created category
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createCategory(@Valid @RequestBody CategoryRequest categoryRequest){
        return categoryService.createCategory(categoryRequest);
    }

    /**
     * Update a category by its id.
     *
     * @param id The id of the category
     * @param categoryRequest The request to update the category
     * @return The updated category
     */
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponse updateCategory(@PathVariable String id,@Valid @RequestBody CategoryRequest categoryRequest){
        return categoryService.updateCategory(id, categoryRequest);
    }

    /**
     * Delete a category by its id.
     *
     * @param id The id of the category
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable String id){
        categoryService.deleteCategory(id);
    }
}
