package com.tasksmart.user.dtos.response;

import com.tasksmart.user.models.User;
import com.tasksmart.sharedLibrary.models.enums.EGender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * This class indicate the dto of user response.
 *
 * @author Duy Hoang
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserResponse {
    /** This is the storage field for the user's identification. */
    private String id;

    /** This is the storage field for the user's name. */
    private String name;

    /** This is the storage field for the username. */
    private String username;

    /** This is the storage field for the user's email. */
    private String email;

    /** This is the storage field for user's gender */
    private EGender gender;

    /** This is the storage field for the user's role. */
    private Set<String> role;

    /** This is the storage field for the user's avatar image. */
    private String profileImagePath;

    /** This is the storage field for the user's position. */
    private String position;

    /** This is the storage field for the user's organization. */
    private String organization;

    /** This is the storage field for the timezone of user area. */
    private int timeZone;

    private User.WorkSpace personalWorkSpace;
    private Set<User.WorkSpace> workspaces;
    private Set<User.Project> projects;
}
