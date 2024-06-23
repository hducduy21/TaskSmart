package com.tasksmart.workspace.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a ListCard in the project.
 * A ListCard has an id, name, listNumber, isCollapse status, and a list of Cards.
 * Each Card in the list is an instance of the Card class.
 *
 * @author Duy Hoang
 */
@Document("listCards")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ListCard {
    /** The unique identifier for the ListCard. */
    @Id
    private String id;

    /** The name of the ListCard. */
    private String name;

    private String projectId;

    @Builder.Default
    private List<String> cardIds = new ArrayList<>();

    /** The collapse status of the ListCard. */
    @Builder.Default
    private boolean isCollapse = false ;

    public ListCard copyWithoutProject() {
        return ListCard.builder()
                .name(this.name)
                .build();
    }
}