package com.tasksmart.workspace.dtos.response;

import com.tasksmart.workspace.models.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ListCardResponse {
    /** The unique identifier for the ListCard. */
    private String id;

    /** The name of the ListCard. */
    private String name;

    /** The collapse status of the ListCard. */
    private boolean isCollapse;

    /** The list of Cards associated with the ListCard. */
    private List<CardResponse> cards;
}
