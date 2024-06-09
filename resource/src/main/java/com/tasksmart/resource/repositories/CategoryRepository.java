package com.tasksmart.resource.repositories;

import com.tasksmart.resource.models.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
}
