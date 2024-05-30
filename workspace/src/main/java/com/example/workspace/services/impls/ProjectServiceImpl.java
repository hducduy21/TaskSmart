package com.example.workspace.services.impls;

import com.example.workspace.dtos.request.CardCreationRequest;
import com.example.workspace.dtos.request.ListCardCreationRequest;
import com.example.workspace.dtos.request.ProjectRequest;
import com.example.workspace.dtos.response.CardResponse;
import com.example.workspace.dtos.response.ListCardResponse;
import com.example.workspace.dtos.response.ProjectGeneralResponse;
import com.example.workspace.dtos.response.ProjectResponse;
import com.example.workspace.models.Project;
import com.example.workspace.repositories.ProjectRepository;
import com.example.workspace.services.ListCardService;
import com.example.workspace.services.ProjectService;
import com.tasksmart.sharedLibrary.dtos.messages.ProjectMessage;
import com.tasksmart.sharedLibrary.dtos.responses.UserGeneralResponse;
import com.tasksmart.sharedLibrary.exceptions.ResourceNotFound;
import com.tasksmart.sharedLibrary.repositories.httpClients.UserClient;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class implements the ProjectService interface.
 *
 * @author Duy Hoang
 */
@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {
    /** The Project's Repository instance for interact with the database.*/
    private final ProjectRepository projectRepository;

    /** The ModelMapper instance for mapper dto.*/
    private final ModelMapper modelMapper;

    /** The ListCardService instance.*/
    private final ListCardService listCardService;

    private final UserClient userClient;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    /** {@inheritDoc} */
    @Override
    public List<ProjectGeneralResponse> getAllProject() {
        return projectRepository.findAll().stream().map(this::getProjectGeneralResponse).toList();
    }

    /** {@inheritDoc} */
    @Override
    public List<ProjectGeneralResponse> getAllProjectByWorkSpace(String workSpaceId){
        return projectRepository.findByWorkSpaceId(workSpaceId).stream().map(this::getProjectGeneralResponse).toList();
    }

    /** {@inheritDoc} */
    @Override
    public ProjectResponse getProjectById(String projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                ()->new ResourceNotFound("Project not found!")
        );
        return getProjectResponse(project);
    }

    /** {@inheritDoc} */
    @Override
    public ProjectGeneralResponse createPersonalProject(ProjectRequest projectRequest){
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public ProjectGeneralResponse saveProject(Project project){

        projectRepository.save(project);
        return getProjectGeneralResponse(project);
    }

    /** {@inheritDoc} */
    @Override
    public ProjectGeneralResponse editProject(String projectId, ProjectRequest projectRequest) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                ()->new ResourceNotFound("Project not found!")
        );
        project.setName(projectRequest.getName());
        project.setDescription(projectRequest.getDescription());
        project.setBackground(projectRequest.getBackground());
        projectRepository.save(project);

        kafkaTemplate.send("project-updation", modelMapper.map(project, ProjectMessage.class));
        return getProjectGeneralResponse(project);
    }

    /** {@inheritDoc} */
    @Override
    public ListCardResponse createListCard(String projectId, ListCardCreationRequest listCardCreationRequest){
        boolean isProjectExist = projectRepository.existsById(projectId);
        if(!isProjectExist){
            throw new ResourceNotFound("Project not found!");
        }
        //check authorization
        return listCardService.createListCard(projectId, listCardCreationRequest);
    }

    /** {@inheritDoc} */
    @Override
    public CardResponse createCard(String projectId, String listCardId, CardCreationRequest cardCreationRequest){
        boolean isProjectExist = projectRepository.existsById(projectId);
        if(!isProjectExist){
            throw new ResourceNotFound("Project not found!");
        }
        //check authorization
        return listCardService.createCard(projectId, listCardId, cardCreationRequest);
    }

    /** {@inheritDoc} */
    @Override
    public void deleteProject(String projectId) {
        if(!projectRepository.existsById(projectId)){
            throw new ResourceNotFound("Project not found!");
        }
        projectRepository.deleteById(projectId);
    }

    /**
     * Get ProjectGeneralResponse from Project.
     *
     * @param project the Project.
     * @return the ProjectGeneralResponse.
     */
    public ProjectGeneralResponse getProjectGeneralResponse(Project project){
        return modelMapper.map(project, ProjectGeneralResponse.class);
    }

    /**
     * Get ProjectResponse from Project.
     *
     * @param project the Project.
     * @return the ProjectResponse.
     */
    public ProjectResponse getProjectResponse(Project project){
        List<UserGeneralResponse> users = project.getUsers().stream().map(userRelation->{
            UserGeneralResponse userGeneralResponse = userClient.getUserGeneralResponse(userRelation.getUserId());
            userGeneralResponse.setRelation(userRelation.getRole().toString());
            return userGeneralResponse;
        }).toList();

        ProjectResponse projectResponse = modelMapper.map(project, ProjectResponse.class);
        projectResponse.setUsers(users);
        projectResponse.setListCards(listCardService.getAllListCardByProject(project.getId()));
        return projectResponse;
    }
}
