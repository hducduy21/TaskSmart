package com.tasksmart.workspace.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MoveCardRequest {
    @NotEmpty(message = "The list card must not be empty")
    private List<MoveCards> Ids;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class MoveCards {
        private String listCardId;
        private List<String> cardIds;
    }
}

