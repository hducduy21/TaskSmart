package com.tasksmart.activity_tracker.repositories;

import com.tasksmart.activity_tracker.models.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActivityRepository extends MongoRepository<Activity, String> {
    void deleteAllByWorkspaceId(String workspaceId);
}
