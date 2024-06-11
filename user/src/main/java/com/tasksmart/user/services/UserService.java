package com.tasksmart.user.services;

import com.tasksmart.user.dtos.request.UserInformationUpdateRequest;
import com.tasksmart.user.dtos.request.UserRegistrationRequest;
import com.tasksmart.user.dtos.response.UserResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Service interface for user-related operations.
 *
 * @author Duy Hoang
 */
public interface UserService {
    /**
     * Retrieves all users.
     *
     * @return A list of all users.
     */
    List<UserResponse> getAllUser();

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The user object corresponding to the provided ID, or null if not found.
     */
    UserResponse getUserByIdOrUsername(String id);

    UserResponse getProfile();

    /**
     * Creates a new user based on the provided registration request.
     *
     * @param userRegistrationRequest The request object containing user registration data.
     * @return The created user object.
     */
    UserResponse createUser(UserRegistrationRequest userRegistrationRequest);

    /**
     * Updates a user by their ID.
     *
     * @param userInformationUpdateRequest object contain user update information request
     * @return The updated user object.
     */
    UserResponse updateUser(UserInformationUpdateRequest userInformationUpdateRequest);

    /**
     * Changes the password of a user.
     *
     * @return The user object with the updated password.
     */
    UserResponse changeUserPassword();

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to delete.
     */
    void deleteById(String id);

    void uploadProfileImage(MultipartFile file);
    byte[] getProfileImage();
}
