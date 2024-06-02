package com.tasksmart.workspace.repositories;

import com.tasksmart.workspace.models.Card;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CardRepository extends MongoRepository<Card, String> {
    List<Card> findByListCardId(String listCardId);
}
