package com.tasksmart.workspace.repositories;

import com.tasksmart.workspace.models.Card;
import com.tasksmart.workspace.models.ListCard;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * ListCardRepository interface for managing List Card operations.
 * This interface is used to manage the List Card operations.
 *
 */
public interface CardRepository extends MongoRepository<Card, String> {
    /**
     * Retrieves all List Cards by Project ID.
     *
     * @return a list of all List Cards by Project ID.
     */
    List<Card> findByIdIn(List<String> cardIds);

    /**
     * Retrieves all List Cards by Project ID.
     * @param projectId
     */
    void deleteAllByProjectId(String projectId);

    /**
     * Retrieves all List Cards by Project ID.
     *
     * @return a list of all List Cards by Project ID.
     */
    List<Card> findByProjectIdAndNameContainsIgnoreCase(String projectId, String name);
}
