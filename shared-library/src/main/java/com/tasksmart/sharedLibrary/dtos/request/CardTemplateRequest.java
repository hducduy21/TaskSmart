package com.tasksmart.sharedLibrary.dtos.request;

import com.tasksmart.sharedLibrary.models.enums.ELevel;
import com.tasksmart.sharedLibrary.models.enums.EStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CardTemplateRequest {
    @NotBlank(message = "Card name is required")
    public String name;

    public String color;

    public String description;

    public EStatus status;

    public ELevel priority;

    public ELevel risk;

    public ELevel effort;

    public LocalDateTime estimate;

    public List<CheckListGroup> checkLists = new ArrayList<>();

    @Getter
    @Setter
    public static class CheckListGroup{
        @NotBlank(message = "Check List Group name is required")
        private String name;

        public List<CheckList> checkLists;
    }

    @Getter
    @Setter
    public static class CheckList{
        @NotBlank(message = "CheckList name is required")
        private String name;

        private boolean checked;
    }

}
