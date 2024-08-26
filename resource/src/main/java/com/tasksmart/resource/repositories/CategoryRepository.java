package com.tasksmart.resource.repositories;

import com.tasksmart.resource.models.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CategoryRepository extends MongoRepository<Category, String> {
    List<Category> findAllByActiveTrue();
    List<Category> findAllByNameContainingAndActiveTrue(String name);
    @Query("{'name': {$regex: ?0, $options: 'i'}, 'active': true}")
    List<Category> findByNameContaining(String keyword);
}
