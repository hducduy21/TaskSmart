package com.tasksmart.resource.repositories;

import com.tasksmart.resource.models.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CategoryRepository extends MongoRepository<Category, String> {
    List<Category> findAllByActiveTrue();
}
