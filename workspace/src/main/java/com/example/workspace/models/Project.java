package com.example.workspace.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Class representing a project model.
 *
 * @author Duy Hoang
 */
@Document("projects")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Project {
    /** The unique identifier for the Project. */
    @Id
    private String id;

    /** The name of the Project. */
    private String name;

    /** The background of the Project. */
    private String background;

    /** The description of the Project. */
    private String description;

    /** The list of ListCards associated with the Project. */
    private String workSpaceId;
}
