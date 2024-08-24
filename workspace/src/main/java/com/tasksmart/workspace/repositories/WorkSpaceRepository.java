package com.tasksmart.workspace.repositories;

import com.tasksmart.workspace.models.WorkSpace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkSpaceRepository extends MongoRepository<WorkSpace, String> {
    @Query("{ 'users.userId': ?0 }")
    List<WorkSpace> findByUserId(String userId);

    @Query("{ 'users.userId': ?0, 'type': ?1}")
    Optional<WorkSpace> findByUserIdAndType(String userId, String type);

    @Query("{ 'users.userId': ?0, 'name': { $regex: ?1, $options: 'i' } }")
    List<WorkSpace> findByUserIdAndNameContain(String userId, String name);
}
