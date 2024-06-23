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
public class ListCardTemplateResponse {
    private String id;
    private String name;
    private boolean isCollapse;

    /** The list of Cards associated with the ListCard. */
    private List<CardTemplateResponse> cards;
}
