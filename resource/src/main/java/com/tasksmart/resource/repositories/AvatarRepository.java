package com.tasksmart.resource.repositories;

import com.tasksmart.resource.models.Avatar;
import com.tasksmart.sharedLibrary.models.enums.EGender;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvatarRepository extends MongoRepository<Avatar, String> {
    List<Avatar> findAllByGender(EGender gender);
}
