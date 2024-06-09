package com.tasksmart.notification.controllers.internals;

import com.tasksmart.notification.services.VerifycationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/${url_base}/internal/verify")
@Slf4j
public class VerifycationInternalController {
    private final VerifycationService verifycationService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public boolean verify(@RequestParam String email,@RequestParam String code) {
        return verifycationService.verifyInternal(email,code);
    }
}