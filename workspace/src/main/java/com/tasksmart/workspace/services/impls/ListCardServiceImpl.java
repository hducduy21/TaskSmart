package com.tasksmart.workspace.services.impls;

import com.tasksmart.sharedLibrary.dtos.request.CardTemplateRequest;
import com.tasksmart.sharedLibrary.dtos.request.ListCardTemplateRequest;
import com.tasksmart.sharedLibrary.dtos.responses.CardTemplateResponse;
import com.tasksmart.sharedLibrary.dtos.responses.ListCardTemplateResponse;
import com.tasksmart.sharedLibrary.exceptions.BadRequest;
import com.tasksmart.sharedLibrary.exceptions.InternalServerError;
import com.tasksmart.sharedLibrary.exceptions.ResourceNotFound;
import com.tasksmart.workspace.dtos.request.CardCreationRequest;
import com.tasksmart.workspace.dtos.request.ListCardCreationRequest;
import com.tasksmart.workspace.dtos.request.TaskGenerateRequest;
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
import org.springframework.util.CollectionUtils;

import java.lang.module.ResolutionException;
import java.util.ArrayList;
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

        CardResponse cardResponse = cardService.createCard(projectId, cardCreationRequest);
        List<String> cardIds;
        if(CollectionUtils.isEmpty(listCard.getCardIds())){
            cardIds = new ArrayList<>();
        } else {
            cardIds = listCard.getCardIds();
        }

        cardIds.add(cardResponse.getId());
        listCard.setCardIds(cardIds);
        listCardRepository.save(listCard);

        return cardResponse;
    }

    @Override
    public ListCardResponse updateListCard(String listCardId, ListCardCreationRequest listCardCreationRequest) {
        ListCard listCard = listCardRepository.findById(listCardId).orElseThrow(
                ()->new ResolutionException("ListCard not found!")
        );

        listCard.setName(listCardCreationRequest.getName());
        listCard.setCollapse(listCardCreationRequest.isCollapse());

        listCardRepository.save(listCard);
        return getListCardResponse(listCard);
    }

    @Override
    public void moveCard(String listCardId, List<String> cardIds) {
        ListCard listCard = listCardRepository.findById(listCardId).orElseThrow(
                ()->new ResolutionException("ListCard not found!")
        );
        listCard.setCardIds(cardIds);
        listCardRepository.save(listCard);
    }

    @Override
    public void deleteListCard(String listCardId) {
        ListCard listCard = listCardRepository.findById(listCardId).orElseThrow(
                ()->new ResolutionException("ListCard not found!")
        );

        try {
            cardService.deleteCardsByIds(listCard.getCardIds());
        } catch (Exception e){
            throw new InternalServerError("Error when delete cards in list card!");
        }
        listCardRepository.deleteById(listCardId);
    }

    @Override
    public List<ListCardResponse> getListCardByIdIn(List<String> listCardIds) {
        return listCardIds.stream().map(listCard->
            getListCardResponse(listCardRepository.findById(listCard).orElseThrow(
                    ()->new ResolutionException("ListCard not found!")
            ))).toList();
    }

    @Override
    public List<ListCardTemplateResponse> getListCardTemplateByIdIn(List<String> listCardIds) {
        return listCardIds.stream().map(listCard->
                getListCardTemplateResponse(listCardRepository.findById(listCard).orElseThrow(
                        ()->new ResolutionException("ListCard not found!")
                ))).toList();
    }

    @Override
    public ListCardTemplateResponse createListCardTemplate(ListCardTemplateRequest listCards) {
        List<CardTemplateResponse> cardTemplateResponses = new ArrayList<>();
        for(CardTemplateRequest cardTemplateRequest: listCards.getCards()){
            CardTemplateResponse cardTemplateResponse = cardService.createCardTemplate(cardTemplateRequest);
            cardTemplateResponses.add(cardTemplateResponse);
        }
        List<String> cardIds = cardTemplateResponses.stream().map(CardTemplateResponse::getId).toList();

        ListCard listCard = modelMapper.map(listCards, ListCard.class);
        listCard.setCardIds(cardIds);
        listCardRepository.save(listCard);

        return getListCardTemplateResponse(listCard);
    }

    @Override
    public String applyTemplate(String listCardId, String projectId) {
        ListCard listCardTemplate = listCardRepository.findById(listCardId).orElseThrow(
                ()->new ResourceNotFound("ListCard not found!")
        );

        ListCard listCard = listCardTemplate.copyWithoutProject();
        listCard.setProjectId(projectId);
        listCardRepository.save(listCard);

        List<String> cardIds = new ArrayList<>();
        listCardTemplate.getCardIds().forEach(cardId->{
            String cardExtractId = cardService.applyTemplate(cardId, projectId);
            cardIds.add(cardExtractId);
        });
        listCard.setCardIds(cardIds);
        listCardRepository.save(listCard);
        return listCard.getId();
    }

    @Override
    public void deleteAllListCardByProject(String projectId) {
        listCardRepository.deleteAllByProjectId(projectId);
    }

    @Override
    public List<String> applyGenerate(String projectId, List<TaskGenerateRequest.ListCardGenerateRequest> listCards) {
        List<String> listCardsId = new ArrayList<>();
        for(TaskGenerateRequest.ListCardGenerateRequest listCardGenerateRequest: listCards){
            ListCard listCard = modelMapper.map(listCardGenerateRequest, ListCard.class);
            listCard.setProjectId(projectId);
            if(listCardGenerateRequest.getCards() != null){
                List<String> cardIds = cardService.applyGenerate(projectId, listCardGenerateRequest.getCards());
                listCard.setCardIds(cardIds);
            }
            listCardRepository.save(listCard);

            listCardsId.add(listCard.getId());
        }
        return listCardsId;
    }

    public ListCardResponse getListCardResponse(ListCard listCard){
        ListCardResponse listCardResponse = modelMapper.map(listCard, ListCardResponse.class);

        List<CardResponse> cards = cardService.getCardsByIdIn(listCard.getCardIds());
        listCardResponse.setCards(cards);

        return listCardResponse;
    }

    public ListCardTemplateResponse getListCardTemplateResponse(ListCard listCard){
        ListCardTemplateResponse listCardTemplaeResponse = modelMapper.map(listCard, ListCardTemplateResponse.class);

        List<CardTemplateResponse> cards = cardService.getCardsTemplateByIdIn(listCard.getCardIds());
        listCardTemplaeResponse.setCards(cards);

        return listCardTemplaeResponse;
    }
}
