package com.tasksmart.workspace.dtos.response;


import com.tasksmart.sharedLibrary.dtos.messages.UnsplashResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * The ProjectGeneralResponse class is used to represent the general information of a project.
 * This class is used to send the general information of a project to the client.
 *
 * @author Duy Hoang
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProjectGeneralResponse {
    /** This is the storage field for the project's identification. */
    private String id;

    /** This is the storage field for the project's name. */
    private String name;

    /** This is the storage field for the project's description. */
    private String description;

    private String backgroundColor;
    private UnsplashResponse backgroundUnsplash;

    private LocalDateTime lastAccessed;

}
