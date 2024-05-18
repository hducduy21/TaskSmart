package com.example.workspace.repositories;

import com.example.workspace.models.WorkSpace;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorkSpaceRepository extends MongoRepository<WorkSpace, String> {
}
