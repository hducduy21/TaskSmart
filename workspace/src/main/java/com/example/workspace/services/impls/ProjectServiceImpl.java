package com.example.workspace.services.impls;

import com.example.workspace.dtos.request.ProjectRequest;
import com.example.workspace.dtos.response.ProjectGeneralResponse;
import com.example.workspace.dtos.response.ProjectResponse;
import com.example.workspace.models.Project;
import com.example.workspace.repositories.ProjectRepository;
import com.example.workspace.services.ListCardService;
import com.example.workspace.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;
    private final ListCardService listCardService;

    @Override
    public List<ProjectGeneralResponse> getAllProject() {
        return projectRepository.findAll().stream().map(this::getProjectGeneralResponse).toList();
    }

    @Override
    public List<ProjectGeneralResponse> getAllProjectByWorkSpace(String workSpaceId){
        return projectRepository.findByWorkSpaceId(workSpaceId).stream().map(this::getProjectGeneralResponse).toList();
    }

    @Override
    public ProjectResponse getProjectById(String projectId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if(project == null){
            return null;
        }
        return getProjectResponse(project);
    }

    @Override
    public ProjectGeneralResponse createPersonalProject(ProjectRequest projectRequest){
        return null;
    }

    @Override
    public ProjectGeneralResponse saveProject(Project project){
        projectRepository.save(project);
        return getProjectGeneralResponse(project);
    }

    @Override
    public ProjectGeneralResponse editProject(String projectId, ProjectRequest projectRequest) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if(project == null){
            return null;
        }
        project.setName(projectRequest.getName());
        project.setDescription(projectRequest.getDescription());
        project.setBackground(projectRequest.getBackground());
        projectRepository.save(project);
        return getProjectGeneralResponse(project);
    }

    @Override
    public void deleteProject() {
    }

    public ProjectGeneralResponse getProjectGeneralResponse(Project project){
        return modelMapper.map(project, ProjectGeneralResponse.class);
    }
    public ProjectResponse getProjectResponse(Project project){
        ProjectResponse projectResponse = modelMapper.map(project, ProjectResponse.class);
        projectResponse.setListCards(listCardService.getAllListCardByProject(project.getId()));
        return projectResponse;
    }
}
