package com.tasksmart.workspace.services;

import com.tasksmart.workspace.dtos.request.*;
import com.tasksmart.workspace.dtos.response.AttachmentResponse;
import com.tasksmart.workspace.dtos.response.CardResponse;
import com.tasksmart.workspace.dtos.response.CheckListGroupResponse;
import org.springframework.web.multipart.MultipartFile;

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
    CardResponse createCard(String projectId, CardCreationRequest cardCreationRequest);

    /**
     * Edits an existing Card.
     *
     * @return the edited Card.
     */
    CardResponse editCard();

    /**
     * Deletes a Card.
     */
    void deleteCard(String cardId);
    void deleteCardsByIds(List<String> cardIds);

    List<CardResponse> getCardsByIdIn(List<String> cardIds);

    CardResponse updateCard(String cardId, CardUpdationRequest cardUpdationRequest);

    List<AttachmentResponse> uploadAttachmentFile(String cardId, MultipartFile[] files);

    CardResponse commentCard(String cardId, CardCommentRequest cardCommentRequest);


    AttachmentResponse updateAttachmentFileInfo(String cardId, CardAttachmentInfoRequest cardAttachmentInfoRequest);

    void deleteAttachmentFile(String cardId, String attachmentId);

    void deleteCommentCard(String cardId, String commentId);

    CardResponse addImplementers(String cardId, CardImplementerRequest cardImplementerRequest);

    CardResponse removeImplementer(String cardId, String implementerId);

    CheckListGroupResponse createCheckListGroup(String cardId, CardCheckListGroupCreationRequest cardCheckListGroupCreationRequest);

    CheckListGroupResponse deleteCheckListGroup(String cardId, String checklistGroupId);

    CheckListGroupResponse addCheckList(String cardId, String checklistGroupId, CardCheckListCreationRequest cardCheckListCreationRequest);

    CheckListGroupResponse updateCheckList(String cardId, String checklistGroupId, String checklistId, CardCheckListUpdationRequest cardCheckListUpdationRequest);

    CheckListGroupResponse removeCheckList(String cardId, String checklistGroupId, String checklistId);
}
