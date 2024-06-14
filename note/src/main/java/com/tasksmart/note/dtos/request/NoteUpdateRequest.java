package com.tasksmart.note.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * This is a dto indicate for note update request
 * @author Duc Nguyen
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NoteUpdateRequest {
    private String title;
    private String content;
    private Boolean pinned;
    private Boolean archived;
    private Boolean deleted;
}
