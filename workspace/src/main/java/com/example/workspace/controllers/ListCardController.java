package com.example.workspace.controllers;

import com.example.workspace.dtos.request.ListCardCreationRequest;
import com.example.workspace.dtos.response.ListCardResponse;
import com.example.workspace.services.ListCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${url_base}/${url_list_card}")
@RequiredArgsConstructor
public class ListCardController {
    private final ListCardService listCardService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ListCardResponse createListCard(@RequestBody ListCardCreationRequest listCardCreationRequest) {
        return listCardService.createListCard(listCardCreationRequest);
    }
}
