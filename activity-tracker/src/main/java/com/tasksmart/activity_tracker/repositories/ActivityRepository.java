package com.tasksmart.activity_tracker.repositories;

import com.tasksmart.activity_tracker.models.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends MongoRepository<Activity, String> {
    @Query(value = "{ 'initiatorId': ?0, 'type': ?1 }", sort = "{ 'updatedAt' : -1 }")
    List<Activity> getRecentProject(String userId, String type);

    @Query("{ 'initiatorId': ?0, 'project.id': ?1, 'type': ?2 }")
    Optional<Activity> findByInitiatorIdAndProjectIdAndType(String initiatorId, String projectId, String type);

    void deleteAllByWorkspaceId(String workspaceId);
}
