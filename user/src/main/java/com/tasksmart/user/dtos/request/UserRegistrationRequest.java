package com.tasksmart.user.dtos.request;

import com.tasksmart.sharedLibrary.models.enums.EGender;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is a dto indicate for user update information request.
 *
 * @author Duy Hoang
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRegistrationRequest {
    /** This is the storage field for the user's name. */
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    /** This is the storage field for the username. */
    @NotBlank(message = "Username cannot be blank")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,20}$", message = "Username must be between 6 and 20 characters and contain only letters and digits")
    private String username;

    /** This is the storage field for the user password. */
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 30, message = "Password must be between 6 and 30 characters")
    private String password;

    @NotBlank(message = "Confirm password cannot be blank")
    private String confirmPassword;

    /** This is the storage field for the user's email. */
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    /** This is the storage field for user's gender */
    private EGender gender;

    @NotBlank(message = "You must provide a verify email code")
    @Pattern(regexp = "^[0-9]{6}$", message = "Email code must be 6 digits long")
    private String verifyCode;

    /** This is the storage field for the timezone of user area. */
    @Min(value = 0, message = "Invalid time zone format")
    private int timeZone;
}
