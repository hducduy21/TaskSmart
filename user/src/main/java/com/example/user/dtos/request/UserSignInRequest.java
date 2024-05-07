package com.example.user.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is a dto indicate for user login request.
 *
 * @author Duy Hoang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignInRequest {
    /** This is the storage field for the user's email. */
    private String email;

    /** This is the storage field for the user password. */
    private String password;
}
