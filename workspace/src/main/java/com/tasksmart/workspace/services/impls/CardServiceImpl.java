package com.tasksmart.workspace.services.impls;

import com.tasksmart.workspace.dtos.request.CardCreationRequest;
import com.tasksmart.workspace.dtos.response.CardResponse;
import com.tasksmart.workspace.models.Card;
import com.tasksmart.workspace.repositories.CardRepository;
import com.tasksmart.workspace.services.CardService;
import com.tasksmart.sharedLibrary.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CardResponse> getAllCard() {
        return List.of();
    }

    @Override
    public CardResponse getCardById(String cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(
                ()->new ResourceNotFound("Card not found!")
        );
        return getCardResponse(card);
    }

    @Override
    public CardResponse createCard(String projectId, CardCreationRequest cardCreationRequest){
        Card card = modelMapper.map(cardCreationRequest, Card.class);
        card.setProjectId(projectId);
        cardRepository.save(card);
        return getCardResponse(card);
    }

    @Override
    public CardResponse editCard() {
        return null;
    }

    @Override
    public void deleteCard(String cardId) {
        boolean exists = cardRepository.existsById(cardId);
        if(!exists){
            throw new ResourceNotFound("Card not found!");
        }
        cardRepository.deleteById(cardId);
    }

    @Override
    public void deleteCardsByIds(List<String> cardIds) {
        cardRepository.deleteAllById(cardIds);
    }

    @Override
    public List<CardResponse> getCardsByIdIn(List<String> cardIds) {
        return cardIds.stream().map(cardId -> getCardResponse(
                cardRepository.findById(cardId).orElseThrow(
                        ()->new ResourceNotFound("Card not found!")
                ))).toList();
    }

    public CardResponse getCardResponse(Card card){
        return modelMapper.map(card, CardResponse.class);
    }
}
