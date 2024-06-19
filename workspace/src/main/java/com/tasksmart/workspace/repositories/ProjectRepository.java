package com.tasksmart.workspace.repositories;

import com.tasksmart.workspace.models.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends MongoRepository<Project, String> {
    List<Project> findByWorkspaceId(String workSpaceId);

    @Query("{ 'id': ?0, 'users.userId': ?1 }")
    Optional<Project> findByProjectIdAndUserId(String projectId, String userId);
}
