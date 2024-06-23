package com.tasksmart.workspace.models;

import com.tasksmart.sharedLibrary.models.CheckListGroup;
import com.tasksmart.sharedLibrary.models.enums.EFileType;
import com.tasksmart.sharedLibrary.models.enums.ELevel;
import com.tasksmart.sharedLibrary.models.enums.EStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Represents a Card in the project.
 * A Card has an id, name, color, description, status, priority, risk, effort, estimate, and a list of checklists.
 * Each checklist has an id, name, and a checked status.
 *
 * @author Duy Hoang
 */
@Document("cards")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Card {
    /** The unique identifier for Card. */
    @Id
    private String id;

    /** The name of Card. */
    public String name;

    /** The color of Card. */
    @Builder.Default
    public String color = "1677ff";

    /** The description of Card. */
    public String description;

    /** The status of Card. */
    public EStatus status;

    /** The priority level of Card. */
    public ELevel priority;

    /** The risk level of Card. */
    public ELevel risk;

    /** The effort level of Card. */
    public ELevel effort;

    /** The estimated completion time for Card. */
    public LocalDateTime estimate;

    /** The list of CheckLists associated with Card. */
    @Builder.Default
    public List<CheckListGroup> checkLists = new ArrayList<>();

    public String projectId;

    @Builder.Default
    public List<Attachment> attachments = new ArrayList<>();

    public Set<String> implementerIds = new HashSet<>();

    public List<Comment> comments = new ArrayList<>();

    public Card copyWithoutProject(){
        return Card.builder()
                .name(this.name)
                .color(this.color)
                .description(this.description)
                .status(this.status)
                .priority(this.priority)
                .risk(this.risk)
                .effort(this.effort)
                .estimate(this.estimate)
                .checkLists(this.checkLists)
                .projectId(projectId)
                .attachments(this.attachments)
                .implementerIds(this.implementerIds)
                .comments(this.comments)
                .build();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class Attachment{
        /**
         * Identity = file name in aws.
         */
        private String fileId = java.util.UUID.randomUUID().toString();

        private String title;

        private EFileType type;

        private String description;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class Comment{
        /**
         * Identity.
         */
        private String id = java.util.UUID.randomUUID().toString();

        private String content;

        private String userId;

        private Date createAt = new Date();
    }
}