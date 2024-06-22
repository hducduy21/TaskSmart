package com.tasksmart.workspace.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CardCheckListUpdationRequest {
    @NotNull(message = "CheckList id is required")
    public boolean checked;
}
