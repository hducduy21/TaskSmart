package com.example.workspace.models;

import com.example.workspace.models.enums.EWorkSpaceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Class representing a workspace model.
 *
 * @author Duy Hoang
 */
@Document("workspaces")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkSpace {
    /** The unique identifier for the WorkSpace. */
    @Id
    private String id;

    /** The name of the WorkSpace. */
    private String name;

    /** The type of the WorkSpace. */
    private EWorkSpaceType type;

    /** The description of the WorkSpace. */
    private String description;
}
