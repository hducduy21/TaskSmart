package com.tasksmart.workspace.services.impls;

import com.tasksmart.sharedLibrary.dtos.messages.ProjectMessage;
import com.tasksmart.sharedLibrary.dtos.messages.UserMessage;
import com.tasksmart.sharedLibrary.dtos.request.ProjectTemplateRequest;
import com.tasksmart.sharedLibrary.dtos.responses.ListCardTemplateResponse;
import com.tasksmart.sharedLibrary.dtos.responses.ProjectTemplateResponse;
import com.tasksmart.sharedLibrary.dtos.messages.UnsplashResponse;
import com.tasksmart.sharedLibrary.dtos.responses.UserGeneralResponse;
import com.tasksmart.sharedLibrary.exceptions.ResourceNotFound;
import com.tasksmart.sharedLibrary.repositories.httpClients.UnsplashClient;
import com.tasksmart.sharedLibrary.repositories.httpClients.UserClient;
import com.tasksmart.sharedLibrary.utils.AuthenticationUtils;
import com.tasksmart.workspace.models.Project;
import com.tasksmart.workspace.models.UserRelation;
import com.tasksmart.workspace.repositories.ProjectRepository;
import com.tasksmart.workspace.repositories.WorkSpaceRepository;
import com.tasksmart.workspace.services.ListCardService;
import com.tasksmart.workspace.services.ProjectInternalService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectInternalServiceImpl implements ProjectInternalService {
    private final ProjectRepository projectRepository;
    private final ListCardService listCardService;
    private final UserClient userClient;
    private final ModelMapper modelMapper;
    private final AuthenticationUtils authenticationUtils;
    private final WorkSpaceRepository workSpaceRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public ProjectTemplateResponse getProjectTemplate(String projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new ResourceNotFound("Project not found")
        );
        return getProjectTemplateResponse(project);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ProjectTemplateResponse createProjectTemplate(ProjectTemplateRequest projectTemplateRequest) {
        Project project = modelMapper.map(projectTemplateRequest, Project.class);
        project.setTemplate(true);

        List<ListCardTemplateResponse> listCardTemplateResponses = new ArrayList<>();
        projectTemplateRequest.getListCards().forEach(listCardTemplateRequest -> {
            ListCardTemplateResponse listCardTemplateResponse = listCardService.createListCardTemplate(listCardTemplateRequest);
            listCardTemplateResponses.add(listCardTemplateResponse);
        });
        List<String> listCardIds = listCardTemplateResponses.stream().map(ListCardTemplateResponse::getId).toList();

        project.setListCardIds(listCardIds);

        projectRepository.save(project);
        return getProjectTemplateResponse(project);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ProjectTemplateResponse updateProjectTemplate(String projectId, ProjectTemplateRequest projectTemplateRequest) {
        return null;
    }

    @Override
    public String applyTemplate(String projectId, String workspaceId, String projectName) {
        String userId = authenticationUtils.getUserIdAuthenticated();
        workSpaceRepository.findById(workspaceId).orElseThrow(
                () -> new ResourceNotFound("Workspace not found")
        );

        Project projectTemplate = projectRepository.findById(projectId).orElseThrow(
                () -> new ResourceNotFound("Project not found")
        );

        Project project = projectTemplate.copyWithoutWorkSpace();
        project.setWorkspaceId(workspaceId);
        UserRelation userRelation = getUserRelation(userId);
        project.setOwner(userRelation);
        project.setName(projectName);

        projectRepository.save(project);

        List<String> listCardIds = new ArrayList<>();
        projectTemplate.getListCardIds().forEach(listCardId -> {
            String listCardExtractId = listCardService.applyTemplate(listCardId, project.getId());
            listCardIds.add(listCardExtractId);
        });

        project.setListCardIds(listCardIds);
        projectRepository.save(project);

        ProjectMessage projectMessage = modelMapper.map(project, ProjectMessage.class);
        projectMessage.setInteractorId(userId);
        kafkaTemplate.send("project-creation",modelMapper.map(project, ProjectMessage.class));

        return project.getId()  ;
    }

    @Override
    public void updateUsersInProject(UserMessage userMessage) {
        List<Project> projects = projectRepository.findProjectByUserId(userMessage.getId());
        projects.forEach(project -> {
            project.getUsers().forEach(userRelation -> {
                if(StringUtils.equals(userRelation.getUserId(), userMessage.getId())){
                    userRelation.setName(userMessage.getName());
                    userRelation.setProfileImagePath(userMessage.getProfileImagePath());
                    userRelation.setUsername(userMessage.getUsername());
                }
            });
            projectRepository.save(project);
        });
    }

    private ProjectTemplateResponse getProjectTemplateResponse(Project project){
        ProjectTemplateResponse projectResponse = modelMapper.map(project, ProjectTemplateResponse.class);
        projectResponse.setListCards(listCardService.getListCardTemplateByIdIn(project.getListCardIds()));
        return projectResponse;
    }

    /**
     * Get UserRelation from UserGeneralResponse.
     *
     * @param userId the user id.
     * @return the UserRelation.
     */
    private UserRelation getUserRelation(String userId){
        UserGeneralResponse userGeneralResponse = userClient.getUserGeneralResponse(userId);
        return modelMapper.map(userGeneralResponse, UserRelation.class);
    }

    private boolean isColor(String background) {
        return StringUtils.startsWith(background, "#") && background.length() == 7;
    }
}
