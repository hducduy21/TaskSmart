package com.tasksmart.workspace.repositories;

import com.tasksmart.workspace.models.ListCard;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ListCardRepository extends MongoRepository<ListCard, String> {
    List<ListCard> findAllByProjectId(String projectId);
}
