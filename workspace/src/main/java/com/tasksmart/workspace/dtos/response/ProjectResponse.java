package com.tasksmart.workspace.dtos.response;

import com.tasksmart.sharedLibrary.dtos.responses.UnsplashResponse;
import com.tasksmart.workspace.models.UserRelation;
import com.tasksmart.sharedLibrary.dtos.responses.UserGeneralResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The ProjectGeneralResponse class is used to represent the detail information of a project.
 * This class is used to send the detail information of a project to the client.
 *
 * @author Duy Hoang
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProjectResponse {
    /** This is the storage field for the project's identification. */
    private String id;

    /** This is the storage field for the project's name. */
    private String name;

    /** This is the storage field for the project's background image. */
    private String backgroundColor;
    private UnsplashResponse backgroundUnsplash;

    /** This is the storage field for the project's description. */
    private String description;

    /** This is the storage field for the project's invite code. */
    @Builder.Default
    private String inviteCode = "";

    /** This is the storage field for the project's list of cards. */
    private List<ListCardResponse> listCards;

    /** This is the storage field for the project's list of users. */
    private List<UserRelation> users;
}
