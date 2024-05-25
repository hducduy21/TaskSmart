package com.example.user.dtos.response;

import lombok.*;

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

    /** This is the storage field for the user's role. */
    private Set<String> role;

    /** This is the storage field for the user's avatar image. */
    private String profileImageId;

    /** This is the storage field for the user's background description. */
    private String profileBackground;

    /** This is the storage field for the timezone of user area. */
    private int timeZone;
}
