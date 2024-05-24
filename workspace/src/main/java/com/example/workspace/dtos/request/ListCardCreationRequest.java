package com.example.workspace.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ListCardCreationRequest {
    /** The name of the ListCard. */
    @NotBlank(message = "The name of list card cannot be blank")
    private String name;

    /** The list number of the ListCard. */
    private Integer listNumber;

    /** The collapse status of the ListCard. */
    private boolean isCollapse;

}
