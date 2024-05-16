package com.example.workspace.controllers;

import com.example.workspace.dtos.request.CardCreationRequest;
import com.example.workspace.dtos.response.CardResponse;
import com.example.workspace.services.CardService;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CardResponse createCard(@RequestBody CardCreationRequest cardCreationRequest) {
        return cardService.createCard(cardCreationRequest);
    }
}
