package com.tasksmart.workspace.services;

import com.tasksmart.workspace.dtos.request.CardCreationRequest;
import com.tasksmart.workspace.dtos.request.ListCardCreationRequest;
import com.tasksmart.workspace.dtos.response.CardResponse;
import com.tasksmart.workspace.dtos.response.ListCardResponse;

import java.util.List;

/**
 * ListCardService interface for managing List Card operations.
 *
 * @author Duy Hoang

 */
public interface ListCardService {
    /**
     * Retrieves all List Cards.
     *
     * @return a list of all List Cards.
     */
    List<ListCardResponse> getAllListCardByProject(String projectId);

    /**
     * Retrieves a List Card by its ID.
     *
     * @return the List Card with the given ID.
     */
    ListCardResponse getListCardById();

    /**
     * Creates a new List Card.
     *
     * @return the created List Card.
     */
    ListCardResponse createListCard(String projectId, ListCardCreationRequest listCardCreationRequest);

    /**
     * Creates a new Card in a List Card.
     *
     * @return the created Card.
     */
    CardResponse createCard(String projectId, String listCardId, CardCreationRequest cardCreationRequest);

    /**
     * Edits an existing List Card.
     *
     * @return the edited List Card.
     */
    ListCardResponse editListCard();

    /**
     * Deletes a List Card.
     */
    void deleteListCard();
}
