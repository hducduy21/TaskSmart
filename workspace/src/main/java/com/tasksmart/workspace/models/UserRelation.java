package com.tasksmart.workspace.models;

import com.tasksmart.workspace.models.enums.EUserRole;
import lombok.*;

import java.util.List;

/**
 * Class representing a user relation model.
 *
 * @author Duy Hoang
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserRelation {
    /** The unique identifier for the User. */
    private String userId;

    /** This is the storage field for the name. */
    private String name;

    /** This is the storage field for the username. */
    private String username;

    /** This is the storage field for the image background. */
    private String profileImageId;

    /** The role of the User. */
    private EUserRole role;
}
