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

    /** The color of the Card. */
    public String color;

    /** The description of the Card. */
    public String description;

    /** The status of the Card. */
    public EStatus status;

    /** The priority level of the Card. */
    public ELevel priority;

    /** The risk level of the Card. */
    public ELevel risk;

    /** The effort level of the Card. */
    public ELevel effort;

    /** The estimated completion time for the Card. */
    public LocalDateTime estimate;

    /** The list of CheckLists associated with the Card. */
    public List<Card.CheckList> checkLists;
}
