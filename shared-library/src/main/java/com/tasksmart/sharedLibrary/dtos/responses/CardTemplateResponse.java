package com.tasksmart.sharedLibrary.dtos.responses;

import com.tasksmart.sharedLibrary.models.CheckListGroup;
import com.tasksmart.sharedLibrary.models.enums.ELevel;
import com.tasksmart.sharedLibrary.models.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CardTemplateResponse {
    private String id;
    public String name;
    public String color;
    public String description;
    public EStatus status;
    public ELevel priority;
    public ELevel risk;
    public ELevel effort;
    public LocalDateTime estimate;
    public List<CheckListGroup> checkLists;
}
