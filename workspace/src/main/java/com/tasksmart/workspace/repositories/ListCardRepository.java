package com.tasksmart.workspace.repositories;

import com.tasksmart.workspace.models.ListCard;
import com.tasksmart.workspace.models.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListCardRepository extends MongoRepository<ListCard, String> {
    List<ListCard> findAllByProjectId(String projectId);
    List<ListCard> findByIdIn(List<String> listCardIds);
    void deleteAllByProjectId(String projectId);
    List<ListCard> findAllByProjectIdAndCardIdsContains(String projectId, String cardId);

    List<ListCard> findByProjectIdAndNameContainsIgnoreCase(String projectId, String name);
}
