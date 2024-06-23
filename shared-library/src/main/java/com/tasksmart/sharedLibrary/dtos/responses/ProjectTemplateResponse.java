package com.tasksmart.sharedLibrary.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProjectTemplateResponse {
    private String id;

    private String name;

    private String background;

    private String description;

    private List<ListCardTemplateResponse> listCards;
}
