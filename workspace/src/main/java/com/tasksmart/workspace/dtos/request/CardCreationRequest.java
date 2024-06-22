package com.tasksmart.workspace.dtos.request;

import com.tasksmart.workspace.models.Card;
import com.tasksmart.workspace.models.enums.ELevel;
import com.tasksmart.workspace.models.enums.EStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
