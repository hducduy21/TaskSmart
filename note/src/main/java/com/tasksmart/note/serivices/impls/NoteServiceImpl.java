package com.tasksmart.note.serivices.impls;

import com.tasksmart.note.dtos.request.NoteRequest;
import com.tasksmart.note.dtos.response.NoteResponse;
import com.tasksmart.note.models.Note;
import com.tasksmart.note.repositories.NoteRepository;
import com.tasksmart.note.serivices.NoteService;
import com.tasksmart.sharedLibrary.exceptions.ResourceNotFound;
import com.tasksmart.sharedLibrary.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of the NoteService interface.
 * This class provides methods to perform various operations related to notes.
 * @author Duc Nguyen
 */
@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final ModelMapper modelMapper;
    private final AuthenticationUtils authenticationUtils;

    @Override
    public NoteResponse createNote(NoteRequest noteRequest) {
        System.out.println("NoteRequest: " + noteRequest.getTitle());
        Note note = modelMapper.map(noteRequest, Note.class);
         String userId = authenticationUtils.getUserIdAuthenticated();
        note.setUserId(userId);
        note.setCreatedAt(LocalDateTime.now());
        note.setDeleted(false);
        note.setPinned(false);
        note.setArchived(false);
        noteRepository.save(note);
        return getNoteResponse(note);
    }

    @Override
    public List<NoteResponse> getAllNotes() {
        return noteRepository.findAll().stream().map(this::getNoteResponse).toList();
    }

    @Override
    public List<NoteResponse> getNotesByUser() {
        String userId = authenticationUtils.getUserIdAuthenticated();

        return noteRepository.findByUserId(userId).stream().map(this::getNoteResponse).toList();
    }

    @Override
    public NoteResponse getNoteById(String noteId) {
        return noteRepository.findById(noteId).map(this::getNoteResponse).orElseThrow(
                ()->new ResourceNotFound("Note not found!")
        );
    }

    @Override
    public NoteResponse editNote(NoteRequest noteRequest, String noteId) {
        Note note = noteRepository.findById(noteId).orElseThrow(
                ()->new ResourceNotFound("Note not found!")
        );
        if (noteRequest.getTitle() != null){
            note.setTitle(noteRequest.getTitle());
        }
        if (noteRequest.getContent() != null){
            note.setContent(noteRequest.getContent());
        }
        if (noteRequest.getPinned() != null){
            note.setPinned(noteRequest.getPinned());
        }
        if (noteRequest.getArchived() != null){
            note.setArchived(noteRequest.getArchived());
        }
        if (noteRequest.getDeleted() != null){
            note.setDeleted(noteRequest.getDeleted());
        }
        noteRepository.save(note);
        return getNoteResponse(note);
    }

    @Override
    public void deleteNote(String noteId) {
        Note note = noteRepository.findById(noteId).orElseThrow(
                ()->new ResourceNotFound("Note not found!")
        );
        noteRepository.delete(note);
    }


    public NoteResponse getNoteResponse(Note note){
        return modelMapper.map(note, NoteResponse.class);
    }

}
