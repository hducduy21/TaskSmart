package com.tasksmart.workspace.services.impls;

import com.tasksmart.sharedLibrary.exceptions.InternalServerError;
import com.tasksmart.workspace.dtos.request.CardCreationRequest;
import com.tasksmart.workspace.dtos.request.ListCardCreationRequest;
import com.tasksmart.workspace.dtos.response.CardResponse;
import com.tasksmart.workspace.dtos.response.ListCardResponse;
import com.tasksmart.workspace.models.ListCard;
import com.tasksmart.workspace.repositories.ListCardRepository;
import com.tasksmart.workspace.services.CardService;
import com.tasksmart.workspace.services.ListCardService;
import com.tasksmart.sharedLibrary.exceptions.ResourceConflict;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
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
        ListCard listCard = listCardRepository.findById(listCardId).orElseThrow(
                ()->new ResolutionException("ListCard not found!")
        );
        if(!listCard.getProjectId().equals(projectId)){
            throw new ResourceConflict("ListCard not belong to this project!");
        }
        return cardService.createCard(listCardId, cardCreationRequest);
    }

    @Override
    public ListCardResponse updateListCard(String listCardId, ListCardCreationRequest listCardCreationRequest) {
        ListCard listCard = listCardRepository.findById(listCardId).orElseThrow(
                ()->new ResolutionException("ListCard not found!")
        );

        listCard.setName(listCardCreationRequest.getName());
        listCard.setListNumber(listCardCreationRequest.getListNumber());
        listCard.setCollapse(listCardCreationRequest.isCollapse());

        listCardRepository.save(listCard);
        return getListCardResponse(listCard);
    }

    @Override
    public void deleteListCard(String listCardId) {
        boolean exists = listCardRepository.existsById(listCardId);
        if(!exists){
            throw new ResolutionException("ListCard not found!");
        }

        try {
            cardService.deleteCardsByListCard(listCardId);
        } catch (Exception e){
            throw new InternalServerError("Error when delete cards in list card!");
        }
        listCardRepository.deleteById(listCardId);
    }

    public ListCardResponse getListCardResponse(ListCard listCard){
        ListCardResponse listCardResponse = modelMapper.map(listCard, ListCardResponse.class);
        List<CardResponse> cards = cardService.getCardsByListCard(listCard.getId());
        listCardResponse.setCards(cards);
        return listCardResponse;
    }
}
