package com.tasksmart.notification.controllers;

import com.tasksmart.notification.models.TokenVerifycation;
import com.tasksmart.notification.services.VerifycationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * The controller for the verifycation service.
 * This controller provides endpoints for verifying email addresses.
 *
 * @author Duy Hoang
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/${url_base}/verify")
@Slf4j
public class VerifycationController {
    /**
     * The verifycation service to handle the verifycation logic.
     */
    private final VerifycationService verifycationService;

    /**
     * Verifies the email address with the given code.
     * @param email The email address to verify.
     * @param code The code to verify the email address with.
     * @return True if the email address is verified, false otherwise.
     */
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public boolean verify(@RequestParam String email, @RequestParam String code){
        return verifycationService.verify(email, code);
    }

    /**
     * Sends a verification request to the given email address.
     * @param email The email address to send the verification request to.
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void verifycationRequest(@RequestParam String email){
        verifycationService.verifycationRequest(email);
    }

}
