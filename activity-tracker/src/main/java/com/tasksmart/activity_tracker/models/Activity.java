package com.tasksmart.activity_tracker.models;

import com.tasksmart.sharedLibrary.models.enums.EActivityType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * The activity model
 *
 * @author Duy Hoang
 */
@Document("activities")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Activity {
    @Id
    private String id;

    private String initiatorId;

    private String workspaceId;

    private ProjectActivity project;

    private String listCardId;

    private String cardId;

    private EActivityType type;

    private String content;

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class ProjectActivity{
        private String id;
        private String name;
    }

}
