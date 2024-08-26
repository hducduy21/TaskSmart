package com.tasksmart.workspace.dtos.response;

import com.tasksmart.sharedLibrary.dtos.messages.UnsplashResponse;
import com.tasksmart.workspace.models.enums.EWorkSpaceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class WorkSpaceGeneralResponse {
    /** This is the storage field for the workspace's identification. */
    private String id;

    /** This is the storage field for the workspace's name. */
    private String name;

    /** This is the storage field for the workspace's type. */
    private EWorkSpaceType type;

    private UnsplashResponse backgroundUnsplash;

    /** This is the storage field for the workspace's description. */
    private String description;
}
