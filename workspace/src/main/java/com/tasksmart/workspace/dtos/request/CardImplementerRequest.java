package com.tasksmart.workspace.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * This is a dto indicate for card member request.
 *
 * @author Duy Hoang
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CardImplementerRequest {
    /** The unique identifier for the User. */
    public List<String> userIds;
}
