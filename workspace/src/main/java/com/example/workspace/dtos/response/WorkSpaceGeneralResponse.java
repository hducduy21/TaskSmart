package com.example.workspace.dtos.response;

import com.example.workspace.models.enums.EWorkSpaceType;
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

    /** This is the storage field for the workspace's description. */
    private String description;
}
