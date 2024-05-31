package com.tasksmart.sharedLibrary.dtos.responses;

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

    /** This is the storage field for the user's role. */
    private String profileImageId;

    private String relation;

    private String personalWorkSpace;
}
