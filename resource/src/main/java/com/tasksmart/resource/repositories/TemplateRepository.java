package com.tasksmart.resource.repositories;

import com.tasksmart.resource.models.Template;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateRepository extends MongoRepository<Template, String> {
    List<Template> findAllByEnabledTrue();
    List<Template> findAllByNameContainsIgnoreCaseAndEnabledTrue(String name);
    List<Template> findAllByEnabledFalse();
    List<Template> findAllByCategoryIdAndEnabledTrue(String categoryId);
    List<Template> findAllByCategoryIdInAndEnabledTrue(List<String> categoryIds);

}
