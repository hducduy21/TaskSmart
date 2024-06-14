package com.tasksmart.note.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Represents a Note in the project.
 * A Note is a card that contains a title, content, and other information.
 * @author Duc Nguyen
 */
@Document("notes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Note {
    /**
     * The unique identifier for the NoteDetail.
     */
    @Id
    private String id;
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
    /**
     * The day create note
     */
    private LocalDateTime createdAt;
}
