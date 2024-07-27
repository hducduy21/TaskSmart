package com.tasksmart.workspace.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DBStructureRequest {
    @NotBlank(message = "Statement cannot be blank.")
    private String statement;
}
