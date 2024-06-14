package com.tasksmart.note.repositories;

import com.tasksmart.note.models.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * The repository for the Note model.
 * This repository is used to interact with the database.
 * @author Duc Nguyen
 */
public interface NoteRepository extends MongoRepository<Note, String> {
    List<Note> findByUserId(String userId);

}
