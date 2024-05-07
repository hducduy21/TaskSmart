package com.example.user.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is a dto indicate for user registration request.
 *
 * @author Duy Hoang
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserInformationUpdateRequest {
    /** This is the storage field for the user's name. */
    private String name;

    /** This is the storage field for the username. */
    private String username;

    /** This is the storage field for the user's email. */
    private String email;

    /** This is the storage field for the user's avatar image. */
    private String profileImageId;

    /** This is the storage field for the user's background description. */
    private String profileBackground;
}
