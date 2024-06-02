package com.tasksmart.workspace.controllers;

import com.tasksmart.workspace.dtos.request.ListCardCreationRequest;
import com.tasksmart.workspace.dtos.response.ListCardResponse;
import com.tasksmart.workspace.services.ListCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${url_base}/${url_list_card}")
@RequiredArgsConstructor
public class ListCardController {
    private final ListCardService listCardService;

}
