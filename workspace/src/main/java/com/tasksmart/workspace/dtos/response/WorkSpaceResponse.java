package com.tasksmart.workspace.dtos.response;

import com.tasksmart.sharedLibrary.dtos.responses.CategoryResponse;
import com.tasksmart.sharedLibrary.dtos.messages.UnsplashResponse;
import com.tasksmart.workspace.models.UserRelation;
import com.tasksmart.workspace.models.enums.EWorkSpaceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The WorkSpaceResponse class is used to represent the general information of a workspace.
 *
 * @author Duy Hoang
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class WorkSpaceResponse {
    /** This is the storage field for the workspace's identification. */
    private String id;

    /** This is the storage field for the workspace's name. */
    private String name;

    /** This is the storage field for the workspace's type. */
    private EWorkSpaceType type;

    /** This is the storage field for the workspace's description. */
    private String description;

    private CategoryResponse category;

    private UnsplashResponse backgroundUnsplash;

    /** This is the storage field for the workspace's projects. */
    private List<ProjectGeneralResponse> projects;

    /** The list of users associated with the WorkSpace. */
    private List<UserRelation> users;
}
