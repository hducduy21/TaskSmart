package com.tasksmart.user.controllers;

import com.tasksmart.user.dtos.request.UpdateEmailRequest;
import com.tasksmart.user.dtos.request.UserInformationUpdateRequest;
import com.tasksmart.user.dtos.request.UserRegistrationRequest;
import com.tasksmart.user.dtos.response.UserResponse;
import com.tasksmart.user.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@Slf4j
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

    @GetMapping("/search")
    public List<UserResponse> searchUser(@RequestParam String keyword){
        return userService.searchUser(keyword);
    }
    /**
     * Retrieves the profile of the currently authenticated user.
     *
     * @return The profile of the currently authenticated user.
     */
    @GetMapping("profile")
    public UserResponse getAccountProfile(){
        return userService.getProfile();
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userIdOrUsername The ID of the user to retrieve.
     * @return The user object corresponding to the provided ID, or null if not found.
     */
    @GetMapping("{userIdOrUsername}")
    public UserResponse getUserByIdOrUsername(@PathVariable String userIdOrUsername){
        System.out.println("Get user by id or username: " + userIdOrUsername);
        return userService.getUserByIdOrUsername(userIdOrUsername);
    }

    /**
     * Creates a new user based on the provided registration request.
     *
     * @param userRegistrationRequest The request object containing user registration data.
     * @return The created user object.
     */
    @PostMapping
    public UserResponse createUser(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest){
        log.info("Create user");
        return userService.createUser(userRegistrationRequest);
    }

    /**
     * Update user information.
     *
     * @param userInformationUpdateRequest The request object containing user registration data.
     * @return The updated user object.
     */
    @PutMapping()
    public UserResponse updateUserInformation(@Valid @RequestBody UserInformationUpdateRequest userInformationUpdateRequest){
        return userService.updateUser(userInformationUpdateRequest);
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

    @PatchMapping("email")
    public UserResponse updateUserEmail(@Valid @RequestBody UpdateEmailRequest updateEmailRequest){
        return userService.updateEmail(updateEmailRequest);
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

    @PostMapping("/profileImage")
    public void uploadProfileImage(@RequestPart MultipartFile file){
        userService.uploadProfileImage(file);
    }
}
