package com.tasksmart.workspace.services;

import com.tasksmart.sharedLibrary.dtos.messages.UserMessage;
import com.tasksmart.sharedLibrary.dtos.request.ProjectTemplateRequest;
import com.tasksmart.sharedLibrary.dtos.responses.ProjectTemplateResponse;

/**
 * WorkSpaceInternalService interface for managing Work Space operations.
 *
 * @author Duy Hoang
 */
public interface ProjectInternalService {
    /**
     * Retrieves a Project Template by its ID.
     *
     * @return the Project Template with the given ID.
     */
    ProjectTemplateResponse getProjectTemplate(String projectId);

    /**
     * Creates a new Project Template.
     *
     * @return the created Project Template.
     */
    ProjectTemplateResponse createProjectTemplate(ProjectTemplateRequest projectTemplateRequest);

    /**
     * Updates an existing Project Template.
     *
     * @return the updated Project Template.
     */
    ProjectTemplateResponse updateProjectTemplate(String projectId, ProjectTemplateRequest projectTemplateRequest);

    /**
     * Deletes a Project Template.
     */
    String applyTemplate(String projectId, String workspaceId, String projectName);

    /**
     * Kafka group
     */
    void updateUsersInProject(UserMessage userMessage);
}
