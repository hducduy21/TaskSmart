package com.example.workspace.services;

import com.example.workspace.dtos.request.CardCreationRequest;
import com.example.workspace.dtos.response.CardResponse;

import java.util.List;
/**
 * CardService interface for managing Card operations.
 *
 * @author Duy Hoang
 */
public interface CardService {

    /**
     * Retrieves all Cards.
     *
     * @return a list of all Cards.
     */
    List<CardResponse> getAllCard();

    /**
     * Retrieves all card of a listcard.
     *
     * @return a list of all Cards.
     */
    List<CardResponse> getCardsByListCard(String listCardId);

    /**
     * Retrieves a Card by its ID.
     *
     * @return the Card with the given ID.
     */
    CardResponse getCardById(String cardId);

    /**
     * Creates a new Card.
     *
     * @return the created Card.
     */
    CardResponse createCard(String listCardId, CardCreationRequest cardCreationRequest);

    /**
     * Edits an existing Card.
     *
     * @return the edited Card.
     */
    CardResponse editCard();

    /**
     * Deletes a Card.
     */
    void deleteCard();
}
