package com.tasksmart.activity_tracker.models;

import com.tasksmart.sharedLibrary.models.enums.EActivityType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

    private String projectId;

    private String listCardId;

    private String cardId;

    private EActivityType type;

    private String content;

}
