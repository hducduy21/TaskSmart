package com.example.workspace.services;

import com.example.workspace.dtos.request.MembersAdditionalRequest;
import com.example.workspace.dtos.request.ProjectRequest;
import com.example.workspace.dtos.request.WorkSpaceRequest;
import com.example.workspace.dtos.response.ProjectGeneralResponse;
import com.example.workspace.dtos.response.WorkSpaceGeneralResponse;
import com.example.workspace.dtos.response.WorkSpaceResponse;

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
     * Adds members to a Work Space.
     *
     * @return the updated Project.
     */
//    ProjectGeneralResponse inviteMembers(String workSpaceId, MembersAdditionalRequest membersAdditionalRequest);
}
