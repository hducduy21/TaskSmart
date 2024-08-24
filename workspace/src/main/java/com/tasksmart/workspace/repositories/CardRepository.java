package com.tasksmart.workspace.repositories;

import com.tasksmart.workspace.models.Card;
import com.tasksmart.workspace.models.ListCard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends MongoRepository<Card, String> {
    List<Card> findByIdIn(List<String> cardIds);
    void deleteAllByProjectId(String projectId);

    List<Card> findByProjectIdAndNameContainsIgnoreCase(String projectId, String name);
}
