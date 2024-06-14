package com.tasksmart.note.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class NoteResponse {
    /**
     * The unique identifier for the Note
     */
    private String id;
    /**
     * The title for the Note
     */
    private String title;
    /**
     * The userId for the Note
     */
    private String userId;
    /**
     * The archived status for the Note
     */
    private Boolean archived;
    /**
     * The pinned status for the Note
     */
    private Boolean pinned;
    /**
     * The deleted status for the Note
     */
    private Boolean deleted;
    /**
     * The content for the Note
     */
    private String content;
    /**
     * The day create for the note
     */
    private LocalDateTime createdAt;
}
