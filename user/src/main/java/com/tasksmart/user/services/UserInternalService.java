package com.tasksmart.user.services;

import com.tasksmart.sharedLibrary.dtos.messages.ProjectMessage;
import com.tasksmart.sharedLibrary.dtos.messages.UserJoinProjectMessage;
import com.tasksmart.sharedLibrary.dtos.messages.UserJoinWorkSpaceMessage;
import com.tasksmart.sharedLibrary.dtos.messages.WorkSpaceMessage;
import com.tasksmart.sharedLibrary.dtos.responses.UserGeneralResponse;

import java.util.List;

/**
 * Service interface for internal user-related operations.
 * This interface provides methods for
 * creating and updating workspaces and projects,
 *
 * @author Duy Hoang
 */
public interface UserInternalService {
    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The user object corresponding to the provided ID, or null if not found.
     */
    UserGeneralResponse getUserGeneralById(String id);

    /**
     * Retrieves a list of users by their IDs.
     *
     * @param userIds The IDs of the users to retrieve.
     * @return The list of user objects corresponding to the provided IDs.
     */
    List<UserGeneralResponse> getUsersGeneralByListId(List<String> userIds);

    /**
     * Retrieves a user by their username.
     *
     * @param workSpaceMessage The username of the user to retrieve.
     * @return The user object corresponding to the provided username, or null if not found.
     */
    void createWorkSpace(WorkSpaceMessage workSpaceMessage);

    /**
     * Updates a user by their ID.
     *
     * @param workSpaceMessage object contain user update information request
     * @return The updated user object.
     */
    void updateWorkSpace(WorkSpaceMessage workSpaceMessage);

    /**
     * Updates a user by their ID.
     *
     * @param userJoinWorkSpaceMessage object contain user update information request
     * @return The updated user object.
     */
    void joinWorkSpace(UserJoinWorkSpaceMessage userJoinWorkSpaceMessage);

    /**
     * Updates a user by their ID.
     *
     * @param projectMessage object contain user update information request
     * @return The updated user object.
     */
    void createProject(ProjectMessage projectMessage);

    /**
     * Updates a user by their ID.
     *
     * @param projectMessage object contain user update information request
     * @return The updated user object.
     */
    void updateProject(ProjectMessage projectMessage);

    /**
     * Updates a user by their ID.
     *
     * @param userJoinProjectMessage object contain user update information request
     * @return The updated user object.
     */
    void joinProject(UserJoinProjectMessage userJoinProjectMessage);
}
