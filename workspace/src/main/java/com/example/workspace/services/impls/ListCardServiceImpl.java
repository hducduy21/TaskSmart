package com.example.workspace.services.impls;

import com.example.workspace.dtos.request.CardCreationRequest;
import com.example.workspace.dtos.request.ListCardCreationRequest;
import com.example.workspace.dtos.response.CardResponse;
import com.example.workspace.dtos.response.ListCardResponse;
import com.example.workspace.models.ListCard;
import com.example.workspace.repositories.ListCardRepository;
import com.example.workspace.services.CardService;
import com.example.workspace.services.ListCardService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ListCardServiceImpl implements ListCardService {
    private final ListCardRepository listCardRepository;
    private final ModelMapper modelMapper;
    private final CardService cardService;

    @Override
    public List<ListCardResponse> getAllListCardByProject(String projectId) {
        List<ListCardResponse> listCardResponses = listCardRepository.findAllByProjectId(projectId).stream().map(this::getListCardResponse).toList();
        return listCardRepository.findAll().stream().map(this::getListCardResponse).toList();
    }

    @Override
    public ListCardResponse getListCardById() {
        return null;
    }

    @Override
    public ListCardResponse createListCard(String projectId, ListCardCreationRequest listCardCreationRequest) {
        ListCard listCard = modelMapper.map(listCardCreationRequest, ListCard.class);
        listCard.setProjectId(projectId);
        listCardRepository.save(listCard);
        return getListCardResponse(listCard);
    }

    @Override
    public CardResponse createCard(String projectId, String listCardId, CardCreationRequest cardCreationRequest) {
        ListCard listCard = listCardRepository.findById(listCardId).orElse(null);
        if(listCard == null){
            return null;
        }
        if(!listCard.getProjectId().equals(projectId)){
            return null;
        }
        return cardService.createCard(listCardId, cardCreationRequest);
    }

    @Override
    public ListCardResponse editListCard() {
        return null;
    }

    @Override
    public void deleteListCard() {

    }

    public ListCardResponse getListCardResponse(ListCard listCard){
        ListCardResponse listCardResponse = modelMapper.map(listCard, ListCardResponse.class);
        List<CardResponse> cards = cardService.getCardsByListCard(listCard.getId());
        listCardResponse.setCards(cards);
        return listCardResponse;
    }
}
