package com.tasksmart.workspace.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CardCheckListCreationRequest {
    @NotBlank(message = "Name is required")
    public String name;
}
