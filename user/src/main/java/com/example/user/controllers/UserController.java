package com.example.user.controllers;

import com.example.user.dtos.request.UserInformationUpdateRequest;
import com.example.user.dtos.request.UserRegistrationRequest;
import com.example.user.dtos.response.UserResponse;
import com.example.user.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling user-related endpoints.
 * This class provides endpoints for user CRUD operations.
 *
 * @author Duy Hoang
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/${url_base}/${url_user}")
public class UserController {
    /** Service instance for performing user operations. */
    private final UserService userService;

    /**
     * Retrieves all users.
     *
     * @return A list of all users.
     */
    @GetMapping
    public List<UserResponse> getAllUser(){
        return userService.getAllUser();
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The user object corresponding to the provided ID, or null if not found.
     */
    @GetMapping("{userId}")
    public UserResponse getUserById(@PathVariable String userId){
        return userService.getUserById(userId);
    }

    /**
     * Creates a new user based on the provided registration request.
     *
     * @param userRegistrationRequest The request object containing user registration data.
     * @return The created user object.
     */
    @PostMapping
    public UserResponse createUser(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest){
        return userService.createUserById(userRegistrationRequest);
    }

    /**
     * Update user information.
     *
     * @param userInformationUpdateRequest The request object containing user registration data.
     * @return The updated user object.
     */
    @PutMapping()
    public UserResponse updateUserInformation(@Valid @RequestBody UserInformationUpdateRequest userInformationUpdateRequest){
        return userService.updateUser(userInformationUpdateRequest); //wait to authentication function.
    }

    /**
     * Changes the password of a user.
     *
     * @return Null, as the authentication function is pending implementation.
     */
    @PatchMapping
    public UserResponse changePassword(){
        //wait to authentication function.
        return null;
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userId The ID of the user to delete.
     */
    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable String userId){
        userService.deleteById(userId);
    }
}
