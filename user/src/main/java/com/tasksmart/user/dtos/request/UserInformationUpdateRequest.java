package com.tasksmart.user.dtos.request;

import com.tasksmart.sharedLibrary.models.enums.EGender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is a dto indicate for user registration request.
 *
 * @author Duy Hoang
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserInformationUpdateRequest {
    /** This is the storage field for the user's name. */
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    /** This is the storage field for the username. */
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_-]*$", message = "Username must contain only letters, numbers, underscores, and hyphens")
    private String username;

    /** This is the storage field for user's gender */
    private EGender gender;

    /** This is the storage field for the user's position. */
    private String position;

    /** This is the storage field for the user's organization. */
    private String organization;
}
