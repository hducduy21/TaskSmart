package com.tasksmart.workspace.services.impls;

import com.tasksmart.sharedLibrary.dtos.request.CardTemplateRequest;
import com.tasksmart.sharedLibrary.dtos.responses.CardTemplateResponse;
import com.tasksmart.sharedLibrary.exceptions.BadRequest;
import com.tasksmart.sharedLibrary.exceptions.InternalServerError;
import com.tasksmart.sharedLibrary.models.CheckList;
import com.tasksmart.sharedLibrary.models.CheckListGroup;
import com.tasksmart.sharedLibrary.services.AwsS3Service;
import com.tasksmart.sharedLibrary.utils.AuthenticationUtils;
import com.tasksmart.sharedLibrary.utils.FileUtil;
import com.tasksmart.workspace.dtos.request.*;
import com.tasksmart.workspace.dtos.response.AttachmentResponse;
import com.tasksmart.workspace.dtos.response.CardResponse;
import com.tasksmart.workspace.dtos.response.CheckListGroupResponse;
import com.tasksmart.workspace.dtos.response.CommentResponse;
import com.tasksmart.workspace.models.*;
import com.tasksmart.workspace.repositories.CardRepository;
import com.tasksmart.workspace.repositories.ListCardRepository;
import com.tasksmart.workspace.repositories.ProjectRepository;
import com.tasksmart.workspace.services.CardService;
import com.tasksmart.sharedLibrary.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final ListCardRepository listCardRepository;
    private final ModelMapper modelMapper;
    private final ProjectRepository projectRepository;
    private final AuthenticationUtils authenticationUtils;
    private final AwsS3Service awsS3Service;
    private final FileUtil fileUtil;

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
        Card card = cardRepository.findById(cardId).orElseThrow(
                ()->new ResourceNotFound("Card not found!")
        );
        List<ListCard> listCards = listCardRepository.findAllByProjectIdAndCardIdsContains(card.getProjectId(), cardId);
        for(ListCard listCard: listCards){
            listCard.getCardIds().remove(cardId);
            listCardRepository.save(listCard);
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

    @Override
    public CardResponse updateCard(String cardId, CardUpdationRequest cardUpdationRequest) {
        String userId = authenticationUtils.getUserIdAuthenticated();
        Card card = cardRepository.findById(cardId).orElseThrow(
                ()->new ResourceNotFound("Card not found!")
        );

        Project project = isUserInTheProject(userId, card.getProjectId());

        if(StringUtils.isNotBlank(cardUpdationRequest.getName())){
            card.setName(cardUpdationRequest.getName());
        }
        if(StringUtils.isNotBlank(cardUpdationRequest.getColor())){
            card.setColor(cardUpdationRequest.getColor());
        }
        if(StringUtils.isNotBlank(cardUpdationRequest.getDescription())){
            card.setDescription(cardUpdationRequest.getDescription());
        }
        if(cardUpdationRequest.getStatus() != null){
            card.setStatus(cardUpdationRequest.getStatus());
        }
        if(cardUpdationRequest.getRisk() != null){
            card.setRisk(cardUpdationRequest.getRisk());
        }
        if(cardUpdationRequest.getPriority() != null){
            card.setPriority(cardUpdationRequest.getPriority());
        }
        if(cardUpdationRequest.getEffort() != null){
            card.setEffort(cardUpdationRequest.getEffort());
        }
        if(cardUpdationRequest.getEstimate() != null){
            card.setEstimate(cardUpdationRequest.getEstimate());
        }

        cardRepository.save(card);
        return getCardResponse(card,project.getUsers());
    }

    @Override
    public List<AttachmentResponse> uploadAttachmentFile(String cardId, MultipartFile[] files) {
        String userId = authenticationUtils.getUserIdAuthenticated();
        Card card = cardRepository.findById(cardId).orElseThrow(
                ()->new ResourceNotFound("Card not found!")
        );

        Project project = isUserInTheProject(userId, card.getProjectId());

        List<Card.Attachment> attachments = card.getAttachments();
        if(attachments == null){
            attachments = new ArrayList<>();
        }

        for(MultipartFile file: files){
            Card.Attachment attachment = new Card.Attachment();
            attachment.setTitle(file.getOriginalFilename());
            attachment.setType(fileUtil.classifyFileType(file));

            String filePath = "projects/" + project.getId();
            try {
                awsS3Service.uploadFile(attachment.getFileId(), filePath , file);
                attachments.add(attachment);
            }catch (Exception e){
                throw new InternalServerError("Error uploading attachment file! Please try later.");
            }
        }

        card.setAttachments(attachments);
        cardRepository.save(card);
        return attachments.stream().map(this::getAttachmentResponse).toList();
    }

    @Override
    public AttachmentResponse updateAttachmentFileInfo(String cardId, CardAttachmentInfoRequest cardAttachmentInfoRequest) {
        String userId = authenticationUtils.getUserIdAuthenticated();
        Card card = cardRepository.findById(cardId).orElseThrow(
                ()->new ResourceNotFound("Card not found!")
        );

        isUserInTheProject(userId, card.getProjectId());

        List<Card.Attachment> attachments = card.getAttachments();
        if(attachments == null){
            throw new ResourceNotFound("Attachment not found!");
        }

        Card.Attachment attachment = attachments.stream().filter(
                att -> att.getFileId().equals(cardAttachmentInfoRequest.getFileId())
        ).findFirst().orElseThrow(
                ()->new ResourceNotFound("Attachment not found!")
        );

        if(StringUtils.isNotBlank(cardAttachmentInfoRequest.getTitle())){
            attachment.setTitle(cardAttachmentInfoRequest.getTitle());
        }
        if(StringUtils.isNotBlank(cardAttachmentInfoRequest.getDescription())){
            attachment.setDescription(cardAttachmentInfoRequest.getDescription());
        }

        cardRepository.save(card);
        return getAttachmentResponse(attachment);
    }

    @Override
    public void deleteAttachmentFile(String cardId, String attachmentId) {
        String userId = authenticationUtils.getUserIdAuthenticated();
        Card card = cardRepository.findById(cardId).orElseThrow(
                ()->new ResourceNotFound("Card not found!")
        );

        Project project = isUserInTheProject(userId, card.getProjectId());

        List<Card.Attachment> attachments = card.getAttachments();
        if(attachments == null){
            throw new ResourceNotFound("Attachment not found!");
        }

        Card.Attachment attachment = attachments.stream().filter(
                att -> att.getFileId().equals(attachmentId)
        ).findFirst().orElseThrow(
                ()->new ResourceNotFound("Attachment not found!")
        );

        attachments.remove(attachment);
        cardRepository.save(card);
    }

    @Override
    public CardResponse commentCard(String cardId, CardCommentRequest cardCommentRequest) {
        String userId = authenticationUtils.getUserIdAuthenticated();
        Card card = cardRepository.findById(cardId).orElseThrow(
                ()->new ResourceNotFound("Card not found!")
        );

        Project project = isUserInTheProject(userId, card.getProjectId());

        Card.Comment comment = new Card.Comment();
        comment.setUserId(userId);
        comment.setContent(cardCommentRequest.getContent());
        comment.setCreateAt(new Date());

        List<Card.Comment> comments = card.getComments();
        if(comments == null){
            comments = new ArrayList<>();
        }
        comments.add(comment);
        card.setComments(comments);
        cardRepository.save(card);
        return getCardResponse(card, project.getUsers());
    }

    @Override
    public void deleteCommentCard(String cardId, String commentId) {
        String userId = authenticationUtils.getUserIdAuthenticated();
        Card card = cardRepository.findById(cardId).orElseThrow(
                ()->new ResourceNotFound("Card not found!")
        );

        isUserInTheProject(userId, card.getProjectId());

        List<Card.Comment> comments = card.getComments();
        if(comments == null){
            throw new ResourceNotFound("Comment not found!");
        }

        Card.Comment comment = comments.stream().filter(
                com -> com.getId().equals(commentId)
        ).findFirst().orElseThrow(
                ()->new ResourceNotFound("Comment not found!")
        );

        comments.remove(comment);
        cardRepository.save(card);
    }

    @Override
    public CardResponse addImplementers(String cardId, CardImplementerRequest cardImplementerRequest) {
        String userId = authenticationUtils.getUserIdAuthenticated();
        Card card = cardRepository.findById(cardId).orElseThrow(
                ()->new ResourceNotFound("Card not found!")
        );

        Project project = isUserInTheProject(userId, card.getProjectId());

        List<String> userIds = cardImplementerRequest.getUserIds();
        Set<String> uniqueUserIds = new HashSet<>(userIds);

        if (userIds.size() != uniqueUserIds.size()) {
            throw new BadRequest("Duplicate user found!");
        }

        card.setImplementerIds(uniqueUserIds);
        cardRepository.save(card);
        return getCardResponse(card, project.getUsers());
    }

    @Override
    public CardResponse removeImplementer(String cardId, String implementerId) {
        String userId = authenticationUtils.getUserIdAuthenticated();
        Card card = cardRepository.findById(cardId).orElseThrow(
                ()->new ResourceNotFound("Card not found!")
        );

        isUserInTheProject(userId, card.getProjectId());

        Set<String> implementers = card.getImplementerIds();
        if(implementers == null){
            throw new ResourceNotFound("Implementer not found!");
        }

        implementers.remove(implementerId);
        cardRepository.save(card);
        return getCardResponse(card);
    }

    @Override
    public CheckListGroupResponse createCheckListGroup(String cardId, CardCheckListGroupCreationRequest cardCheckListGroupCreationRequest) {
        String userId = authenticationUtils.getUserIdAuthenticated();
        Card card = cardRepository.findById(cardId).orElseThrow(
                ()->new ResourceNotFound("Card not found!")
        );

        isUserInTheProject(userId, card.getProjectId());

        CheckListGroup group = CheckListGroup.builder()
                .id(UUID.randomUUID().toString())
                .name(cardCheckListGroupCreationRequest.getName())
                .checkLists(new ArrayList<>())
                .build();

        List<CheckListGroup> checkLists = card.getCheckLists();
        if(checkLists == null){
            checkLists = new ArrayList<>();
        }
        checkLists.add(group);
        cardRepository.save(card);
        return getCheckListGroupResponse(group);
    }

    @Override
    public CheckListGroupResponse deleteCheckListGroup(String cardId, String checklistGroupId) {
        String userId = authenticationUtils.getUserIdAuthenticated();
        Card card = cardRepository.findById(cardId).orElseThrow(
                ()->new ResourceNotFound("Card not found!")
        );

        isUserInTheProject(userId, card.getProjectId());

        List<CheckListGroup> checkLists = card.getCheckLists();
        if(checkLists == null){
            throw new ResourceNotFound("Check list group not found!");
        }

        CheckListGroup checkListGroup = checkLists.stream().filter(
                clg -> clg.getId().equals(checklistGroupId)
        ).findFirst().orElseThrow(
                ()->new ResourceNotFound("Check list group not found!")
        );

        checkLists.remove(checkListGroup);
        cardRepository.save(card);
        return getCheckListGroupResponse(checkListGroup);
    }

    @Override
    public CheckListGroupResponse addCheckList(String cardId, String checklistGroupId, CardCheckListCreationRequest cardCheckListCreationRequest) {
        String userId = authenticationUtils.getUserIdAuthenticated();
        Card card = cardRepository.findById(cardId).orElseThrow(
                ()->new ResourceNotFound("Card not found!")
        );

        isUserInTheProject(userId, card.getProjectId());

        List<CheckListGroup> checkLists = card.getCheckLists();
        if(checkLists == null){
            throw new ResourceNotFound("CheckList not found!");
        }

        CheckListGroup checkListGroup = checkLists.stream().filter(
                clg -> StringUtils.equals(clg.getId(), checklistGroupId)
        ).findFirst().orElseThrow(
                ()->new ResourceNotFound("CheckList not found!")
        );

        checkListGroup.checkLists.forEach(checkList -> {
            if(StringUtils.equals(checkList.getName(), cardCheckListCreationRequest.getName())){
                throw new ResourceNotFound("CheckList already exists!");
            }
        });

        CheckList checkList = CheckList.builder()
                .name(cardCheckListCreationRequest.getName()).build();

        checkListGroup.getCheckLists().add(checkList);

        cardRepository.save(card);

        return getCheckListGroupResponse(checkListGroup);
    }

    @Override
    public CheckListGroupResponse updateCheckList(String cardId, String checklistGroupId, String checklistId, CardCheckListUpdationRequest cardCheckListUpdationRequest) {
        String userId = authenticationUtils.getUserIdAuthenticated();
        Card card = cardRepository.findById(cardId).orElseThrow(
                ()->new ResourceNotFound("Card not found!")
        );

        isUserInTheProject(userId, card.getProjectId());

        List<CheckListGroup> checkLists = card.getCheckLists();
        if(checkLists == null){
            throw new ResourceNotFound("Check List Group not found!");
        }

        CheckListGroup checkListGroup = checkLists.stream().filter(
                clg -> StringUtils.equals(clg.getId(), checklistGroupId)
        ).findFirst().orElseThrow(
                ()->new ResourceNotFound("Check List Group not found!")
        );

        CheckList checkList = checkListGroup.getCheckLists().stream().filter(
                cl -> StringUtils.equals(cl.getId(), checklistId)
        ).findFirst().orElseThrow(
                ()->new ResourceNotFound("CheckList not found!")
        );

        checkList.setChecked(cardCheckListUpdationRequest.isChecked());
        cardRepository.save(card);

        return getCheckListGroupResponse(checkListGroup);
    }

    @Override
    public CheckListGroupResponse removeCheckList(String cardId, String checklistGroupId, String checkListId) {
        String userId = authenticationUtils.getUserIdAuthenticated();
        Card card = cardRepository.findById(cardId).orElseThrow(
                ()->new ResourceNotFound("Card not found!")
        );

        isUserInTheProject(userId, card.getProjectId());

        List<CheckListGroup> checkLists = card.getCheckLists();
        if(checkLists == null){
            throw new ResourceNotFound("Check list group not found!");
        }

        CheckListGroup checkListGroup = checkLists.stream().filter(
                clg -> clg.getId().equals(checklistGroupId)
        ).findFirst().orElseThrow(
                ()->new ResourceNotFound("Check list group not found!")
        );

        CheckList checkList = checkListGroup.getCheckLists().stream().filter(
                cl -> StringUtils.equals(cl.getId(), checkListId)
        ).findFirst().orElseThrow(
                ()->new ResourceNotFound("CheckList not found!")
        );

        checkListGroup.getCheckLists().remove(checkList);
        cardRepository.save(card);
        return getCheckListGroupResponse(checkListGroup);
    }

    @Override
    public List<CardTemplateResponse> getCardsTemplateByIdIn(List<String> cardIds) {
        return cardIds.stream().map(cardId -> getCardTemplateResponse(
                cardRepository.findById(cardId).orElseThrow(
                        ()->new ResourceNotFound("Card not found!")
                ))).toList();
    }

    @Override
    public CardTemplateResponse createCardTemplate(CardTemplateRequest cardTemplateRequest) {
        Card card = modelMapper.map(cardTemplateRequest, Card.class);
        List<CheckListGroup> checkLists = cardTemplateRequest.getCheckLists().stream().map(
                checkListGroupTemplate -> {
                    return CheckListGroup.builder()
                            .id(UUID.randomUUID().toString())
                            .name(checkListGroupTemplate.getName())
                            .checkLists(checkListGroupTemplate.getCheckLists().stream().map(
                                    checkListTemplate -> {
                                        return CheckList.builder()
                                                .id(UUID.randomUUID().toString())
                                                .name(checkListTemplate.getName())
                                                .checked(checkListTemplate.isChecked())
                                                .build();
                                    }
                            ).toList())
                            .build();
                }
        ).toList();
        card.setCheckLists(checkLists);
        cardRepository.save(card);
        return getCardTemplateResponse(card);
    }

    @Override
    public String applyTemplate(String cardId, String projectId) {
        Card cardTemplate = cardRepository.findById(cardId).orElseThrow(
                ()->new ResourceNotFound("Card not found!")
        );

        Card card = cardTemplate.copyWithoutProject();
        card.setProjectId(projectId);
        cardRepository.save(card);
        return card.getId();
    }

    @Override
    public void deleteAllCardByProject(String id) {
        cardRepository.deleteAllByProjectId(id);
    }

    @Override
    public List<String> applyGenerate(String projectId, List<TaskGenerateRequest.CardGenerateRequest> cards) {
        List<String> cardIds = new ArrayList<>();
        for(TaskGenerateRequest.CardGenerateRequest cardGenerateRequest: cards){
            Card card = modelMapper.map(cardGenerateRequest, Card.class);
            card.setProjectId(projectId);
            if(cardGenerateRequest.getCheckLists() != null){
                List<CheckListGroup> checkListGroup = card.getCheckLists().stream().map(checkListGroupGenerateRequest -> {
                    CheckListGroup group = CheckListGroup.builder()
                            .id(UUID.randomUUID().toString())
                            .name(checkListGroupGenerateRequest.getName())
                            .build();
                    if(checkListGroupGenerateRequest.getCheckLists() != null){
                        group.setCheckLists(checkListGroupGenerateRequest.getCheckLists().stream().map(
                                checkListGenerateRequest -> {
                                    return CheckList.builder()
                                            .id(UUID.randomUUID().toString())
                                            .name(checkListGenerateRequest.getName())
                                            .checked(checkListGenerateRequest.isChecked())
                                            .build();
                                }).toList());
                    }
                    return group;
                }).toList();

                card.setCheckLists(checkListGroup);
            }

            cardRepository.save(card);
            cardIds.add(card.getId());
        }
        return cardIds;
    }

    private Project isUserInTheProject(String userId, String projectId){
        Project project = projectRepository.findById(projectId).orElseThrow(
                ()->new ResourceNotFound("Project not found!")
        );
        for(UserRelation user: project.getUsers()){
            if(user.getUserId().equals(userId)){
                return project;
            }
        }
        throw new ResourceNotFound("User not in the project!");
    }

    public CardResponse getCardResponse(Card card, List<UserRelation> userRelations){
        CardResponse cardResponse = modelMapper.map(card, CardResponse.class);
        System.out.println(userRelations.size());

        //handle comment response
        List<CommentResponse> commentResponses = card.getComments().stream().map(comment -> {
            Optional<UserRelation> userRelationOptional = userRelations.stream().filter(
                    userRelation -> userRelation.getUserId().equals(comment.getUserId())
            ).findFirst();
            UserRelation user = userRelationOptional.orElse(UserRelation.builder().name("Unactivated").userId("none").build());
            return CommentResponse.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .createdAt(comment.getCreateAt())
                    .user(user)
                    .build();
        }).toList();

        List<UserRelation> implementers = userRelations.stream().filter(
                userRelation -> card.getImplementerIds().contains(userRelation.getUserId())
        ).toList();

        cardResponse.setComments(commentResponses);
        cardResponse.setImplementers(implementers);

        return modelMapper.map(card, CardResponse.class);
    }

    public CardResponse getCardResponse(Card card){
        Project project = projectRepository.findById(card.getProjectId()).orElseThrow(
                ()->new ResourceNotFound("Project not found!")
        );
        CardResponse cardResponse = modelMapper.map(card, CardResponse.class);

        //handle comment response
        List<CommentResponse> commentResponses = card.getComments().stream().map(comment -> {
            Optional<UserRelation> userRelationOptional = project.getUsers().stream().filter(
                    userRelation -> userRelation.getUserId().equals(comment.getUserId())
            ).findFirst();
            UserRelation user = userRelationOptional.orElse(UserRelation.builder().name("Unactivated").userId("none").build());
            return CommentResponse.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .createdAt(comment.getCreateAt())
                    .user(user)
                    .build();
        }).toList();

        List<UserRelation> implementers = project.getUsers().stream().filter(
                userRelation -> card.getImplementerIds().contains(userRelation.getUserId())
        ).toList();

        cardResponse.setComments(commentResponses);
        cardResponse.setImplementers(implementers);
        return cardResponse;
    }

    public CardTemplateResponse getCardTemplateResponse (Card card){
        CardTemplateResponse cardResponse = modelMapper.map(card, CardTemplateResponse.class);
        return cardResponse;
    }

    public AttachmentResponse getAttachmentResponse(Card.Attachment attachment){
        return modelMapper.map(attachment, AttachmentResponse.class);
    }

    public CheckListGroupResponse getCheckListGroupResponse(CheckListGroup checkListGroup){
        return modelMapper.map(checkListGroup, CheckListGroupResponse.class);
    }
}
