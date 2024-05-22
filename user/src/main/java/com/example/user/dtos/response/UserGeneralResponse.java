package com.example.user.dtos.response;

import com.example.user.models.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class indicate the dto of user general response.
 *
 * @author Duy Hoang
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserGeneralResponse {
    /** This is the storage field for the user's identification. */
    private String id;

    /** This is the storage field for the user's name. */
    private String name;

    /** This is the storage field for the username. */
    private String username;

    /** This is the storage field for the user's email. */
    private String email;

    /** This is the storage field for the user's role. */
    private ERole role;
}
