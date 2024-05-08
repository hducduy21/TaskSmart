package com.example.user.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    /** This is the storage field for the user password. */
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
