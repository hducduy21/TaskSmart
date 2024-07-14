package com.tasksmart.workspace.controllers;

import com.tasksmart.workspace.dtos.request.*;
import com.tasksmart.workspace.dtos.response.AttachmentResponse;
import com.tasksmart.workspace.dtos.response.CardResponse;
import com.tasksmart.workspace.dtos.response.CheckListGroupResponse;
import com.tasksmart.workspace.services.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("${url_base}/${url_card}")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @GetMapping("{cardId}")
    @ResponseStatus(HttpStatus.OK)
    public CardResponse getCardById(@PathVariable String cardId) {
        return cardService.getCardById(cardId);
    }

    @PatchMapping("{cardId}")
    @ResponseStatus(HttpStatus.OK)
    public CardResponse updateCard(@PathVariable String cardId, @Valid @RequestBody CardUpdationRequest cardUpdationRequest) {
        return cardService.updateCard(cardId, cardUpdationRequest);
    }

    @DeleteMapping("{cardId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCard(@PathVariable String cardId) {
        cardService.deleteCard(cardId);
    }

    @PostMapping("{cardId}/attachment")
    @ResponseStatus(HttpStatus.OK)
    public List<AttachmentResponse> uploadAttachmentFile(@PathVariable String cardId, @RequestPart("files") MultipartFile[] files) {
        return cardService.uploadAttachmentFile(cardId, files);
    }

    @PutMapping("{cardId}/attachment/{attachmentId}")
    @ResponseStatus(HttpStatus.OK)
    public AttachmentResponse updateAttachmentFileInfo(@PathVariable String cardId, @Valid @RequestBody CardAttachmentInfoRequest cardAttachmentInfoRequest) {
        return cardService.updateAttachmentFileInfo(cardId, cardAttachmentInfoRequest);
    }

    @DeleteMapping("{cardId}/attachment/{attachmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAttachmentFile(@PathVariable String cardId, @PathVariable String attachmentId) {
        cardService.deleteAttachmentFile(cardId, attachmentId);
    }

    @PostMapping("{cardId}/comment")
    @ResponseStatus(HttpStatus.OK)
    public CardResponse commentCard(@PathVariable String cardId, @Valid @RequestBody CardCommentRequest cardCommentRequest) {
        return cardService.commentCard(cardId, cardCommentRequest);
    }

    @DeleteMapping("{cardId}/comment/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void commentCard(@PathVariable String cardId, @PathVariable String commentId) {
        cardService.deleteCommentCard(cardId, commentId);
    }

    @PostMapping("{cardId}/implementers")
    @ResponseStatus(HttpStatus.OK)
    public CardResponse addMembers(@PathVariable String cardId, @Valid @RequestBody CardImplementerRequest cardImplementerRequest) {
        return cardService.addImplementers(cardId, cardImplementerRequest);
    }

    @PostMapping("{cardId}/implementers/{implementerId}")
    @ResponseStatus(HttpStatus.OK)
    public CardResponse removeImplementer(@PathVariable String cardId, @PathVariable String implementerId) {
        return cardService.removeImplementer(cardId, implementerId);
    }

    @PostMapping("{cardId}/checklists")
    @ResponseStatus(HttpStatus.OK)
    public CheckListGroupResponse createCheckListGroup(@PathVariable String cardId, @Valid @RequestBody CardCheckListGroupCreationRequest cardCheckListGroupCreationRequest) {
        return cardService.createCheckListGroup(cardId, cardCheckListGroupCreationRequest);
    }

    @DeleteMapping("{cardId}/checklists/{checklistGroupId}")
    @ResponseStatus(HttpStatus.OK)
    public CheckListGroupResponse deleteCheckListGroup(@PathVariable String cardId,
                                                           @PathVariable String checklistGroupId) {
        return cardService.deleteCheckListGroup(cardId, checklistGroupId);
    }

    @PostMapping("{cardId}/checklists/{checklistGroupId}")
    @ResponseStatus(HttpStatus.OK)
    public CheckListGroupResponse createCheckList(@PathVariable String cardId,
                                        @PathVariable String checklistGroupId,
                                        @Valid @RequestBody CardCheckListCreationRequest cardCheckListCreationRequest) {
        return cardService.addCheckList(cardId, checklistGroupId, cardCheckListCreationRequest);
    }

    @PatchMapping("{cardId}/checklists/{checklistGroupId}/{checklistId}")
    @ResponseStatus(HttpStatus.OK)
    public CheckListGroupResponse updateCheckList(@PathVariable String cardId,
                                        @PathVariable String checklistGroupId,
                                        @PathVariable String checklistId,
                                        @Valid @RequestBody CardCheckListUpdationRequest cardCheckListUpdationRequest
    ) {
        return cardService.updateCheckList(cardId, checklistGroupId, checklistId, cardCheckListUpdationRequest);
    }

    @DeleteMapping("{cardId}/checklists/{checklistGroupId}/{checklistId}")
    @ResponseStatus(HttpStatus.OK)
    public CheckListGroupResponse removeCheckList(@PathVariable String cardId, @PathVariable String checklistGroupId, @PathVariable String checklistId) {
        return cardService.removeCheckList(cardId, checklistGroupId, checklistId);
    }
}
