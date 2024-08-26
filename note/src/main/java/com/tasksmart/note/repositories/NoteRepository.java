package com.tasksmart.note.repositories;

import com.tasksmart.note.models.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * The repository for the Note model.
 * This repository is used to interact with the database.
 * @author Duc Nguyen
 */
public interface NoteRepository extends MongoRepository<Note, String> {
    /**
     * Find all notes by userId
     * @param userId The userId of the notes
     * @return A list of notes with the given userId
     */
    List<Note> findByUserId(String userId);

    /**
     * Find all notes by userId and title containing keyword
     * @param keyword The keyword to search for in the title
     * @return A list of notes with the given userId and title containing the keyword
     */
    @Query("{'title': {$regex: ?0, $options: 'i'}}")
    List<Note> findByTitleContaining(String keyword);

    /**
     * Find all notes by userId and content containing keyword
     * @param keyword The keyword to search for in the content
     * @return A list of notes with the given userId and content containing the keyword
     */
    List<Note> findAllByUserIdAndTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String userId, String title, String content);
}
