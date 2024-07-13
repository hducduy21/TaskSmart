package com.tasksmart.workspace.repositories;

import com.tasksmart.workspace.models.Card;
import com.tasksmart.workspace.models.ListCard;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CardRepository extends MongoRepository<Card, String> {
    List<Card> findByIdIn(List<String> cardIds);
    void deleteAllByProjectId(String projectId);

    List<Card> findByProjectIdAndNameContainsIgnoreCase(String projectId, String name);
}
