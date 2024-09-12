package com.tasksmart.sharedLibrary.dtos.responses;

import com.tasksmart.sharedLibrary.models.CheckListGroup;
import com.tasksmart.sharedLibrary.models.enums.ELevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskGenResponse {
    private List<ListCard> listCards;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheckList {
        private String name;

        @Builder.Default
        private boolean checked = false;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheckListGroup {
        private String name;
        private List<CheckList> checkList;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Card {
        @Builder.Default
        private String id = UUID.randomUUID().toString();

        private String name;
        private String description;
        private String reference;
        private ELevel priority;
        private ELevel risk;
        private ELevel effort;
        private String startTime;
        private String estimate;

        private List<CheckList> checkLists;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListCard {
        @Builder.Default
        private String id = UUID.randomUUID().toString();

        private String name;
        private List<Card> cards;
    }
}
