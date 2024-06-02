package com.tasksmart.workspace.controllers;

import com.tasksmart.workspace.dtos.response.CardResponse;
import com.tasksmart.workspace.services.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${url_base}/${url_card}")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @GetMapping("{cardId}")
    @ResponseStatus(HttpStatus.OK)
    public CardResponse getCardById(@PathVariable String cardId) {
        return cardService.getCardById(cardId);
    }

}
