package com.tasksmart.workspace.dtos.request;

import com.tasksmart.sharedLibrary.dtos.request.CardTemplateRequest;
import com.tasksmart.sharedLibrary.dtos.responses.TaskGenResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskGenerateRequest {
    List<ListCardGenerateRequest> listCards;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListCardGenerateRequest {
        @NotBlank(message = "List name is required")
        private String name;

        private List<CardGenerateRequest> cards;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CardGenerateRequest {
        @NotBlank(message = "Card name is required")
        private String name;

        private String description;


        private List<CheckListGroupGenerateRequest> checkLists;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheckListGenerateRequest {
        @NotBlank(message = "Card name is required")
        private String name;

        @Builder.Default
        private boolean checked = false;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheckListGroupGenerateRequest {
        @NotBlank(message = "Card name is required")
        private String name;
        private List<CheckListGenerateRequest> checkList;
    }
}
