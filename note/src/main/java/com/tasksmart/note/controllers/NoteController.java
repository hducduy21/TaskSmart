package com.tasksmart.note.controllers;

import com.tasksmart.note.dtos.request.NoteRequest;
import com.tasksmart.note.dtos.response.CustomResponse;
import com.tasksmart.note.dtos.response.NoteResponse;
import com.tasksmart.note.serivices.NoteService;
import com.tasksmart.sharedLibrary.dtos.responses.SearchAllResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * Controller class for handling note-related endpoints.
 * This class provides endpoints for note CRUD operations.
 * @author Duc Nguyen
 */
@RestController
@RequestMapping("/api/note")
@RequiredArgsConstructor
public class NoteController {

    /**
     * Note service instance for handling note-related operations.
     */
    private final NoteService noteService;

    /**
     * Endpoint for creating a new note.
     * @param note NoteRequest object containing note data.
     * @return ResponseEntity containing the created note.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomResponse<NoteResponse>> createNote(@Valid @RequestBody NoteRequest note){
        CustomResponse<NoteResponse> response = new CustomResponse<>(
                "Note created successfully",
                201,
                1,
                noteService.createNote(note)
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint for retrieving a note by its id.
     * @param noteId String containing the note id.
     * @return ResponseEntity containing the retrieved note.
     */
    @GetMapping("{noteId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomResponse<NoteResponse>> getNoteById(@PathVariable String noteId){
        CustomResponse<NoteResponse> response = new CustomResponse<>(
                "Note retrieved successfully",
                200,
                1,
                noteService.getNoteById(noteId)
        );
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint for retrieving all notes.
     * @param archived Boolean indicating whether to retrieve archived notes.
     * @return ResponseEntity containing a list of retrieved notes.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomResponse<List<NoteResponse>>> getAllNotes(@RequestParam(required = false) Boolean archived ){
        CustomResponse<List<NoteResponse>> response = new CustomResponse<>(
            "Notes retrieved successfully",
            200,
            noteService.getAllNotes(archived).size(),
            noteService.getAllNotes(archived)
        );
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint for searching notes.
     * @param query String containing the search query.
     * @return SearchAllResponse containing the search results.
     */
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public SearchAllResponse searchNotes(@RequestParam String query){
        return noteService.searchNotes(query);
    }

    /**
     * Endpoint for searching only notes.
     * @param query String containing the search query.
     * @return List of NoteResponse containing the search results.
     */
    @GetMapping("/search/notes")
    @ResponseStatus(HttpStatus.OK)
    public List<NoteResponse> searchOnlyNotes(@RequestParam String query){
        return noteService.searchOnlyNotes(query);
    }

    /**
     * Endpoint for editing a note.
     * @param note NoteRequest object containing the updated note data.
     * @param noteId String containing the note id.
     * @return ResponseEntity containing the edited note.
     */
    @PatchMapping("{noteId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomResponse<NoteResponse>> editNote(@RequestBody NoteRequest note, @PathVariable String noteId){
        CustomResponse<NoteResponse> response = new CustomResponse<>(
                "Note edited successfully",
                200,
                1,
                noteService.editNote(note, noteId)
        );
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint for archiving a note.
     * @param noteId String containing the note id.
     */
    @DeleteMapping("{noteId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteNote(@PathVariable String noteId){
        noteService.deleteNote(noteId);
    }

}
