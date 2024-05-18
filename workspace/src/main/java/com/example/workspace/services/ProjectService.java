package com.example.workspace.services;

import com.example.workspace.dtos.request.CardCreationRequest;
import com.example.workspace.dtos.request.ListCardCreationRequest;
import com.example.workspace.dtos.request.ProjectRequest;
import com.example.workspace.dtos.response.CardResponse;
import com.example.workspace.dtos.response.ListCardResponse;
import com.example.workspace.dtos.response.ProjectGeneralResponse;
import com.example.workspace.dtos.response.ProjectResponse;
import com.example.workspace.models.Project;

import java.util.List;

/**
 * This interface is used to define the methods that will be implemented in the ProjectServiceImpl class.
 *
 * @author Duy Hoang
 */
public interface ProjectService {
    /**
     * Retrieves all Projects.
     *
     * @return a list of all Projects.
     */
    List<ProjectGeneralResponse> getAllProject();

    /**
     * Retrieves all Projects by Work Space.
     *
     * @return a list of all Projects by Work Space.
     */
    List<ProjectGeneralResponse> getAllProjectByWorkSpace(String workSpaceId);

    /**
     * Retrieves a Project by its ID.
     *
     * @return the Project with the given ID.
     */
    ProjectResponse getProjectById(String projectId);

    /**
     * Saves a Project.
     * Make sure the workspace in the project wants to save exists,
     * so this function is only called at the workspace service.
     *
     * @return the saved Project.
     */
    ProjectGeneralResponse saveProject(Project project);

    /**
     * Creates a new project in personal workspace.
     *
     * @return the created Project.
     */
    ProjectGeneralResponse createPersonalProject(ProjectRequest projectRequest);

    /**
     * Creates a new List Card.
     *
     * @return the created List Card.
     */
    ListCardResponse createListCard(String projectId, ListCardCreationRequest listCardCreationRequest);

    /**
     * Creates a new Card.
     *
     * @return the created List Card.
     */
    CardResponse createCard(String projectId, String listCardId, CardCreationRequest cardCreationRequest);

    /**
     * Edits an existing Project.
     *
     * @return the edited Project.
     */
    ProjectGeneralResponse editProject(String projectId, ProjectRequest projectRequest);

    /**
     * Deletes a Project.
     */
    void deleteProject(String projectId);
}
