package com.tasksmart.notification.services;

import com.tasksmart.notification.models.TokenVerifycation;

/**
 * Interface for VerifycationService
 * This interface provides methods to verify a user's email.
 * @author Duy Hoang
 */
public interface VerifycationService {
    /**
     * Verifies the email address with the given code.
     * @param email The email address to verify.
     * @param code The code to verify the email address with.
     * @return True if the email address is verified, false otherwise.
     */
    boolean verify( String email, String code);

    /**
     * Verifies the email address with the given code.
     * @param email The email address to verify.
     * @param code The code to verify the email address with.
     * @return True if the email address is verified, false otherwise.
     */
    boolean verifyInternal( String email, String code);

    /**
     * Sends a verification request to the given email address.
     * @param email The email address to send the verification request to.
     */
    TokenVerifycation verifycationRequest(String email);
}
