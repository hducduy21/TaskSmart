package com.tasksmart.sharedLibrary.repositories.httpClients;

import com.tasksmart.sharedLibrary.dtos.responses.CategoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "repository", url = "http://localhost:8805")
public interface CategoryClient {
    @GetMapping(value = "api/repositories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    CategoryResponse getCategory(@PathVariable String id);
}
