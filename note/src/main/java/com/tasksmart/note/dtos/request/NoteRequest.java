package com.tasksmart.note.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 *
 * This is a dto indicate for note creation request
 * @author Duc Nguyen
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NoteRequest {
    @NotBlank(message = "Note title cannot be blank")
    /**
     * The title of the Note.
     */
    private String title;
    /**
     * The userId of the Note.
     */
    private String userId;
    /**
     * The archived status of the Note.
     */
    private Boolean archived;
    /**
     * The pinned of the note
     */
    private Boolean pinned;
    /**
     * The deleted status of the Note.
     */
    private Boolean deleted;
    /**
     * The content of this note
     */
    private String content;
}
