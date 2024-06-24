package com.tasksmart.workspace.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * This is a dto indicate for card creation request.
 *
 * @author Duy Hoang
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CardCreationRequest {
    /** The name of the Card. */
    @NotBlank(message = "Card's name cannot be blank")
    public String name;

}
