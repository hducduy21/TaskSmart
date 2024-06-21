package com.tasksmart.workspace.dtos.request;

import com.tasksmart.workspace.models.Card;
import com.tasksmart.workspace.models.enums.ELevel;
import com.tasksmart.workspace.models.enums.EStatus;
import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * This is a dto indicate for card update request.
 *
 * @author Duy Hoang
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CardUpdationRequest {
    /** The name of Card. */
    public String name;

    /** The color of Card. */
    public String color;

    /** The description of Card. */
    public String description;

    public ELevel priority;

    /** The status of Card. */
    public EStatus status;

    /** The risk level of Card. */
    public ELevel risk;

    /** The effort level of Card. */
    public ELevel effort;

    /** The estimated completion time for Card. */
    @Future(message = "The estimate must be in the future")
    public LocalDateTime estimate;

}
