package com.tasksmart.user.services.impls.clients;

import com.tasksmart.sharedLibrary.dtos.responses.SearchAllResponse;
import com.tasksmart.sharedLibrary.repositories.httpClients.NoteClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NoteService {

    private final NoteClient noteClient;

    @CircuitBreaker(name = "noteService", fallbackMethod = "getNoteSearchFallback")
    public SearchAllResponse search(String query){
        return noteClient.search(query);
    }

    public SearchAllResponse getNoteSearchFallback(String query, Throwable throwable){
        return SearchAllResponse.builder().build();
    }
}
