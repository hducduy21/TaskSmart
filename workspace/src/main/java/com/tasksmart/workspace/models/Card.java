package com.tasksmart.workspace.models;

import com.tasksmart.sharedLibrary.models.EFileType;
import com.tasksmart.workspace.models.enums.ELevel;
import com.tasksmart.workspace.models.enums.EStatus;
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
    /** The unique identifier for the Card. */
    @Id
    private String id;

    /** The name of the Card. */
    public String name;

    /** The color of the Card. */
    @Builder.Default
    public String color = "1677ff";

    /** The description of the Card. */
    public String description;

    /** The status of the Card. */
    public EStatus status;

    /** The priority level of the Card. */
    public ELevel priority;

    /** The risk level of the Card. */
    public ELevel risk;

    /** The effort level of the Card. */
    public ELevel effort;

    /** The estimated completion time for the Card. */
    public LocalDateTime estimate;

    /** The list of CheckLists associated with the Card. */
    @Builder.Default
    public List<CheckListGroup> checkLists = new ArrayList<>();

    public String projectId;

    @Builder.Default
    public List<Attachment> attachments = new ArrayList<>();

    public Set<String> implementerIds = new HashSet<>();

    public List<Comment> comments = new ArrayList<>();

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