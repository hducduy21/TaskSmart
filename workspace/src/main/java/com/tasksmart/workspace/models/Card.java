package com.tasksmart.workspace.models;

import com.tasksmart.workspace.models.enums.ELevel;
import com.tasksmart.workspace.models.enums.EStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    public String color;

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
    public List<CheckList> checkLists = new ArrayList<>();

    public String projectId;

    /**
     * Represents a CheckList in the Card.
     * A CheckList has an id, name, and a checked status.
     */
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class CheckList{
        /**
         * The name of the CheckList.
         */
        private String name;

        /**
         * The checked status of the CheckList.
         */
        private boolean checked;
    }
}