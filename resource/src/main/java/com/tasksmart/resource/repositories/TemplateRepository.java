package com.tasksmart.resource.repositories;

import com.tasksmart.resource.models.Template;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TemplateRepository extends MongoRepository<Template, String> {
}
