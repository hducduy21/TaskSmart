package com.example.workspace.dtos.request;

import com.example.workspace.models.enums.EWorkSpaceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is a dto indicate for workspace creation request.
 *
 * @author Duy Hoang
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorkSpaceRequest {
    /** The name of the WorkSpace. */
    private String name;

    /** The description of the WorkSpace. */
    private String description;
}
