package com.tasksmart.notification.controllers;

import com.tasksmart.notification.models.TokenVerifycation;
import com.tasksmart.notification.services.VerifycationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/${url_base}/verify")
@Slf4j
public class VerifycationController {
    private final VerifycationService verifycationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean verifycationRequest(@RequestParam String code){
        return verifycationService.verify(code);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void verifycationRequest(){
        verifycationService.verifycationRequest();
    }
}
