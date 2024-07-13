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
    NoteResponse createNote(NoteRequest note);
    NoteResponse getNoteById(String noteId);
    NoteResponse editNote(NoteRequest note, String noteId);
    void deleteNote(String noteId);
    SearchAllResponse searchNotes(String keyword);
    List<NoteResponse> getAllNotes(Boolean archived);

}
