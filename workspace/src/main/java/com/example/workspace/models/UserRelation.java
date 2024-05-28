package com.example.workspace.models;

import com.example.workspace.models.enums.EUserRole;
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

    /** The role of the User. */
    private EUserRole role;
}
