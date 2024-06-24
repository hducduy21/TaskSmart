package com.tasksmart.resource.repositories;

import com.tasksmart.resource.models.Template;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TemplateRepository extends MongoRepository<Template, String> {
    List<Template> findAllByEnabledTrue();
    List<Template> findAllByEnabledFalse();
    List<Template> findAllByCategoryIdAndEnabledTrue(String categoryId);
    List<Template> findAllByCategoryIdInAndEnabledTrue(List<String> categoryIds);
}
