package com.tasksmart.note.controllers;

import com.tasksmart.note.dtos.request.NoteRequest;
import com.tasksmart.note.dtos.response.NoteResponse;
import com.tasksmart.note.serivices.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

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
    public NoteResponse createNote(@Valid @RequestBody NoteRequest note){
        return noteService.createNote(note);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NoteResponse> getAllNotes(){
        return noteService.getAllNotes();
    }


    @GetMapping("{noteId}")
    @ResponseStatus(HttpStatus.OK)
    public NoteResponse getNoteById(@PathVariable String noteId){
        return noteService.getNoteById(noteId);
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public List<NoteResponse> getNotesByUser(){
        return noteService.getNotesByUser();
    }

    @PatchMapping("{noteId}")
    @ResponseStatus(HttpStatus.OK)
    public NoteResponse editNote(@RequestBody NoteRequest note, @PathVariable String noteId){
        return noteService.editNote(note, noteId);
    }

    @DeleteMapping("{noteId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteNote(@PathVariable String noteId){
        noteService.deleteNote(noteId);
    }
}
