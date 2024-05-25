package com.example.user.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @Email(message = "Invalid email format")
    private String email;

    /** This is the storage field for the username. */
    @Pattern(regexp = "^[a-zA-Z0-9]{6,20}$", message = "Username must be between 6 and 20 characters and contain only letters and digits")
    private String username;

    /** This is the storage field for the user password. */
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
