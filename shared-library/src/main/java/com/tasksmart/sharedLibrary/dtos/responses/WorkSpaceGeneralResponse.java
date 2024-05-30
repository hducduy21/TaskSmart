package com.tasksmart.sharedLibrary.dtos.responses;

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
}
