package com.tasksmart.workspace.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is a dto indicate for project creation request.
 *
 * @author Duy Hoang
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProjectRequest {
    /** The name of the Project. */
    @NotBlank(message = "Project's name cannot be blank")
    private String name;

    /** The background of the Project. */
    private String background;

    /** The description of the Project. */
    private String description;
}
