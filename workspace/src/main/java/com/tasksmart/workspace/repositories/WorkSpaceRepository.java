package com.tasksmart.workspace.repositories;

import com.tasksmart.workspace.models.WorkSpace;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorkSpaceRepository extends MongoRepository<WorkSpace, String> {
}
