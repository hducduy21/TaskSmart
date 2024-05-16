package com.example.workspace.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ListCardCreationRequest {
    /** The unique identifier for the ListCard. */
    private String id;

    /** The name of the ListCard. */
    private String name;

    /** The list number of the ListCard. */
    private Integer listNumber;

    /** The collapse status of the ListCard. */
    private boolean isCollapse;

    /** The list of Cards associated with the ListCard. */
    private List<String> cardIds;
}
