package com.tasksmart.resource.repositories;

import com.tasksmart.resource.models.Avatar;
import com.tasksmart.sharedLibrary.models.enums.EGender;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AvatarRepository extends MongoRepository<Avatar, String> {
    List<Avatar> findAllByGender(EGender gender);
}
