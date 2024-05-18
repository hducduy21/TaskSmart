package com.example.workspace.repositories;

import com.example.workspace.models.Card;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CardRepository extends MongoRepository<Card, String> {
    List<Card> findByListCardId(String listCardId);
}
