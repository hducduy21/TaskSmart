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

    private final NoteService noteService;

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

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public SearchAllResponse searchNotes(@RequestParam String query){
        return noteService.searchNotes(query);
    }

    @GetMapping("/search/notes")
    @ResponseStatus(HttpStatus.OK)
    public List<NoteResponse> searchOnlyNotes(@RequestParam String query){
        return noteService.searchOnlyNotes(query);
    }

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

    @DeleteMapping("{noteId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteNote(@PathVariable String noteId){
        noteService.deleteNote(noteId);
    }

}
