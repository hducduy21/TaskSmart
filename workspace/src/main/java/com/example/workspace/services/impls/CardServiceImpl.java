package com.example.workspace.services.impls;

import com.example.workspace.dtos.request.CardCreationRequest;
import com.example.workspace.dtos.response.CardResponse;
import com.example.workspace.models.Card;
import com.example.workspace.repositories.CardRepository;
import com.example.workspace.services.CardService;
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
    public List<CardResponse> getCardsByListCard(String listCardId) {
        return cardRepository.findByListCardId(listCardId).stream().map(this::getCardResponse).toList();
    }

    @Override
    public CardResponse getCardById(String cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(
                ()->new ResourceNotFound("Card not found!")
        );
        return getCardResponse(card);
    }

    @Override
    public CardResponse createCard(String listCardId, CardCreationRequest cardCreationRequest){
        Card card = modelMapper.map(cardCreationRequest, Card.class);
        card.setListCardId(listCardId);
        cardRepository.save(card);
        return getCardResponse(card);
    }

    @Override
    public CardResponse editCard() {
        return null;
    }

    @Override
    public void deleteCard() {

    }

    public CardResponse getCardResponse(Card card){
        return modelMapper.map(card, CardResponse.class);
    }
}
