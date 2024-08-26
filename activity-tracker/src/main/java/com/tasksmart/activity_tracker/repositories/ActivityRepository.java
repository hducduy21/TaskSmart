package com.tasksmart.activity_tracker.repositories;

import com.tasksmart.activity_tracker.models.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Activity repository
 *
 * @author Duy Hoang
 */
@Repository
public interface ActivityRepository extends MongoRepository<Activity, String> {

    /**
     * Get recent project activities
     *
     * @param userId User id
     * @param type Activity type
     * @return List of activities
     */
    @Query(value = "{ 'initiatorId': ?0, 'type': ?1 }", sort = "{ 'updatedAt' : -1 }")
    List<Activity> getRecentProject(String userId, String type);

    /**
     * Find by initiator id, project id and type
     * @param initiatorId
     * @param projectId
     * @param type
     * @return
     */
    @Query("{ 'initiatorId': ?0, 'project._id': ?1, 'type': ?2 }")
    Optional<Activity> findByInitiatorIdAndProjectIdAndType(String initiatorId, String projectId, String type);

    /**
     * Delete all activities by workspace id
     *
     * @param workspaceId Workspace id
     */
    void deleteAllByWorkspaceId(String workspaceId);
}
