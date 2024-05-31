package com.example.workspace.repositories;

import com.example.workspace.models.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends MongoRepository<Project, String> {
    List<Project> findByWorkSpaceId(String workSpaceId);

    @Query("{ 'id': ?0, 'users.userId': ?1 }")
    Optional<Project> findByProjectIdAndUserId(String projectId, String userId);
}
