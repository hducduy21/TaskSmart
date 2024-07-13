package com.tasksmart.workspace.services;

import com.tasksmart.sharedLibrary.dtos.responses.SearchAllResponse;
import com.tasksmart.workspace.dtos.request.ListCardCreationRequest;
import com.tasksmart.workspace.dtos.request.ProjectRequest;
import com.tasksmart.workspace.dtos.request.WorkSpaceRequest;
import com.tasksmart.workspace.dtos.request.WorkspaceUpdateImage;
import com.tasksmart.workspace.dtos.response.*;

import java.util.List;

/**
 * WorkSpaceService interface for managing Work Space operations.
 *
 * @author  Duy Hoang
 */
public interface WorkSpaceService {
    /**
     * Retrieves all Work Spaces.
     *
     * @return a list of all Work Spaces.
     */
    List<WorkSpaceGeneralResponse> getAllWorkSpace();

    /**
     * Retrieves a Work Space by its ID.
     *
     * @return the Work Space with the given ID.
     */
    WorkSpaceResponse getWorkSpaceById(String Id);

    /**
     * Creates a new Work Space.
     *
     * @return the created Work Space.
     */
    WorkSpaceGeneralResponse createWorkSpace(WorkSpaceRequest workSpaceRequest);

    /**
     * Creates a new Project in current workspace.
     *
     * @return the created Project.
     */
    ProjectGeneralResponse createProject(String workSpaceId, ProjectRequest projectRequest);
    /**
     * Edits an existing Work Space.
     *
     * @return the edited Work Space.
     */
    WorkSpaceGeneralResponse editWorkSpace(String workSpaceId, WorkSpaceRequest workSpaceRequest);

    /**
     * Deletes a Work Space.
     */
    void deleteWorkSpace(String workSpaceId);

    /**
     * Checks if a Work Space exists.
     *
     * @return true if the Work Space exists, false otherwise.
     */
    boolean isWorkSpaceExist(String workSpaceId);

    /**
     * Joins a Project by invite code.
     *
     * @param workspaceId project id
     * @param inviteCode invite code
     * @return ProjectResponse
     */
    WorkSpaceResponse joinWorkSpaceByInviteCode(String workspaceId, String inviteCode);

    /**
     * Refreshes the invite code of a Project.
     *
     * @param workspaceId project id
     * @return ProjectResponse
     */
    InviteCodeResponse updateInviteCode(String workspaceId, Boolean isPublic, Boolean refresh);

    WorkSpaceGeneralResponse setUnsplashBackground(String workSpaceId, WorkspaceUpdateImage unsplashId);

    SearchAllResponse search(String query);
}
