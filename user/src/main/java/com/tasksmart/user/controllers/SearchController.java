package com.tasksmart.user.controllers;

import com.tasksmart.sharedLibrary.dtos.responses.SearchAllResponse;
import com.tasksmart.user.services.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/${url_base}/{url_search}")
@Slf4j
public class SearchController {
    private final SearchService searchService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public SearchAllResponse searchAll(@RequestParam(required = false) String query) {
        return searchService.searchAll(query);
    }
}
