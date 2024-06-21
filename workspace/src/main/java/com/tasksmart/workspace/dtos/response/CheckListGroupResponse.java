package com.tasksmart.workspace.dtos.response;

import com.tasksmart.workspace.models.Card;
import com.tasksmart.workspace.models.CheckList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CheckListGroupResponse {
    public String id;
    public String name;
    public List<CheckList> checkList;
}
