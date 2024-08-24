package com.tasksmart.workspace.repositories;

import com.tasksmart.workspace.models.Project;
import com.tasksmart.workspace.models.WorkSpace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {
    List<Project> findByWorkspaceId(String workSpaceId);

    @Query("{ 'users.userId': ?0 }")
    List<Project> findByUserId(String userId);

    @Query("{ 'id': ?0, 'users.userId': ?1 }")
    Optional<Project> findByProjectIdAndUserId(String projectId, String userId);

    @Query("{ 'users.userId': ?0}")
    List<Project> findProjectByUserId(String userId);


    @Query("{ 'users.userId': ?0, 'name': { $regex: ?1, $options: 'i' } }")
    List<Project> findByUserIdAndNameContain(String userId, String name);

    @Query("{ 'users.userId': ?0, 'name': { $regex: ?1, $options: 'i' }, 'workspaceId': ?2 }")
    List<Project> findByUserIdAndNameContainingAndWorkspaceId(String userId, String name, String workspaceId);
}
