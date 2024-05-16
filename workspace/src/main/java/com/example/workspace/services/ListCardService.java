package com.example.workspace.services;

import com.example.workspace.dtos.request.ListCardCreationRequest;
import com.example.workspace.dtos.response.ListCardResponse;

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
    ListCardResponse createListCard(ListCardCreationRequest listCardCreationRequest);

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
