package com.tasksmart.workspace.services;

import com.tasksmart.sharedLibrary.dtos.responses.SearchAllResponse;
import com.tasksmart.sharedLibrary.dtos.responses.TaskGenResponse;
import com.tasksmart.workspace.dtos.request.*;
import com.tasksmart.workspace.dtos.response.*;
import com.tasksmart.workspace.models.Project;
import org.springframework.web.multipart.MultipartFile;

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
    ProjectGeneralResponse createProject(ProjectRequest projectRequest);

    /**
     * Creates a new List Card.
     *
     * @return the created List Card.
     */
    ListCardResponse createListCard(String projectId, ListCardCreationRequest listCardCreationRequest);

    ListCardResponse updateListCard(String projectId, String listCardId, ListCardCreationRequest listCardCreationRequest);
    void deleteListCard(String projectId, String listCardId);

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

    /**
     * Joins a Project by invite code.
     *
     * @param projectId project id
     * @param inviteCode invite code
     * @return ProjectResponse
     */
    ProjectResponse joinProjectByInviteCode(String projectId, String inviteCode);

    /**
     * Refreshes the invite code of a Project.
     *
     * @param projectId project id
     * @return ProjectResponse
     */
    InviteCodeResponse updateInviteCode(String projectId, Boolean isPublic, Boolean refresh);

    ProjectResponse moveListCard(String projectId, MoveListCardRequest moveListCardRequest);
    ProjectResponse moveCard(String projectId, MoveCardRequest moveCardRequest);

    byte[] viewImage(String projectId, String assetId);

    void deleteAllProjectByWorkSpace(String workSpaceId);

    byte[] getProjectDocument(String projectId);

    String putProjectDocument(String projectId, MultipartFile file);

    TaskGenResponse generateTask(String projectId);

    ProjectResponse updateBackground(String projectId, String background);
    SearchAllResponse search(String query);
}
