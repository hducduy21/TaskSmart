package com.tasksmart.sharedLibrary.repositories.httpClients;

import com.tasksmart.sharedLibrary.configs.interceptors.FeignRequestInterceptor;
import com.tasksmart.sharedLibrary.dtos.responses.SearchAllResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "note", url = "http://localhost:8804", configuration = {FeignRequestInterceptor.class})
public interface NoteClient {
    @GetMapping(value = "api/note/search", produces = MediaType.APPLICATION_JSON_VALUE)
    SearchAllResponse search(@RequestParam String query);
}
