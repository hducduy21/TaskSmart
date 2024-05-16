package com.example.workspace.repositories;

import com.example.workspace.models.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProjectRepository extends MongoRepository<Project, String> {
    List<Project> findByWorkSpaceId(String workSpaceId);
}
