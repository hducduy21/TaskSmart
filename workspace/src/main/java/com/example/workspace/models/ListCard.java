package com.example.workspace.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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
public class ListCard {
    /** The unique identifier for the ListCard. */
    @Id
    private String id;

    /** The name of the ListCard. */
    private String name;

    /** The list number of the ListCard. */
    private Integer listNumber;

    /** The identifier for the project contain. */
    private String projectId;

    /** The collapse status of the ListCard. */
    private boolean isCollapse;
}