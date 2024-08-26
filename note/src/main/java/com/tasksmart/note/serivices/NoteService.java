package com.tasksmart.note.serivices;

import com.tasksmart.note.dtos.request.NoteRequest;
import com.tasksmart.note.dtos.response.NoteResponse;
import com.tasksmart.note.models.Note;
import com.tasksmart.sharedLibrary.dtos.responses.SearchAllResponse;

import java.util.List;

/***
 * Interface for NoteService
 * This interface provides methods to perform various operations related to notes.
 * @author Duc Nguyen
 */

public interface NoteService {
    /**
     * Create a new note
     * @param note NoteRequest object containing note data
     * @return NoteResponse object containing the created note
     */
    NoteResponse createNote(NoteRequest note);

    /**
     * Get a note by its id
     * @param noteId String containing the note id
     * @return NoteResponse object containing the retrieved note
     */
    NoteResponse getNoteById(String noteId);

    /**
     * Edit a note
     * @param note NoteRequest object containing the new note data
     * @param noteId String containing the note id
     * @return NoteResponse object containing the edited note
     */
    NoteResponse editNote(NoteRequest note, String noteId);

    /**
     * Archive a note
     * @param noteId String containing the note id
     */
    void deleteNote(String noteId);

    /**
     * Archive a note
     * @param keyword String containing the note id
     */
    SearchAllResponse searchNotes(String keyword);

    /**
     * Get all notes
     * @param archived Boolean containing the archived status of the notes
     * @return List of NoteResponse objects containing all notes
     */
    List<NoteResponse> getAllNotes(Boolean archived);

    /**
     * Get all notes
     * @param keyword String containing the keyword to search for in the notes
     * @return List of NoteResponse objects containing all notes
     */
    List<NoteResponse> searchOnlyNotes(String keyword);

}
