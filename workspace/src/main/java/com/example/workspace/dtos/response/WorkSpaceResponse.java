package com.example.workspace.dtos.response;

import com.example.workspace.models.enums.EWorkSpaceType;
import com.tasksmart.sharedLibrary.dtos.responses.UserGeneralResponse;
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

    /** This is the storage field for the workspace's projects. */
    private List<ProjectGeneralResponse> projects;

    private List<UserGeneralResponse> users;
}
