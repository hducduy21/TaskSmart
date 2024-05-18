package com.example.user.models;

import com.example.user.models.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class indicate the User model.
 *
 * @author Duy Hoang
 */
@Document("users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    /** This is the storage field for the user's identification. */
    private String id;

    /** This is the storage field for the user's name. */
    private String name;

    /** This is the storage field for the username. */
    private String username;

    /** This is the storage field for the password encoded. */
    private String password;

    /** This is the storage field for the user's email. */
    private String email;

    /** This is the storage field for the user's role. */
    private ERole role;

    /** This is the storage field for the user's avatar image. */
    private String profileImageId;

    /** This is the storage field for the user's background description. */
    private String profileBackground;

    /** This is the storage field for the timezone of user area. */
    private int timeZone;

    /** This is the storage field for the authentication status. */
    private boolean enabled;

    /** This is the storage field for the user's operability. */
    private boolean locked;
}
