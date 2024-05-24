package com.example.workspace.services.impls;

import com.example.workspace.dtos.request.ProjectRequest;
import com.example.workspace.dtos.request.WorkSpaceRequest;
import com.example.workspace.dtos.response.ProjectGeneralResponse;
import com.example.workspace.dtos.response.WorkSpaceGeneralResponse;
import com.example.workspace.dtos.response.WorkSpaceResponse;
import com.example.workspace.models.Project;
import com.example.workspace.models.WorkSpace;
import com.example.workspace.models.enums.EWorkSpaceType;
import com.example.workspace.repositories.WorkSpaceRepository;
import com.example.workspace.services.ProjectService;
import com.example.workspace.services.WorkSpaceService;
import com.tasksmart.sharedLibrary.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WorkSpaceServiceImpl implements WorkSpaceService {
    private final WorkSpaceRepository workSpaceRepository;
    private final ModelMapper modelMapper;
    private final ProjectService projectService;

    @Override
    public List<WorkSpaceGeneralResponse> getAllWorkSpace() {
        return workSpaceRepository.findAll().stream().map(this::getWorkSpaceGeneralResponse).toList();
    }

    @Override
    public WorkSpaceResponse getWorkSpaceById(String Id) {
        WorkSpace workSpace = workSpaceRepository.findById(Id).orElseThrow(
                ()->new ResourceNotFound("WorkSpace not found!")
        );
        return getWorkSpaceResponse(workSpace);
    }

    @Override
    public WorkSpaceGeneralResponse createWorkSpace(WorkSpaceRequest workSpaceRequest) {
        WorkSpace workSpace = modelMapper.map(workSpaceRequest, WorkSpace.class);
        workSpace.setType(EWorkSpaceType.Private);
        workSpaceRepository.save(workSpace);
        return getWorkSpaceGeneralResponse(workSpace);
    }

    @Override
    public ProjectGeneralResponse createProject(String workSpaceId, ProjectRequest projectRequest) {
        if (!workSpaceRepository.existsById(workSpaceId)){
            throw new ResourceNotFound("WorkSpace not found!");
        }
        Project project = modelMapper.map(projectRequest, Project.class);
        project.setWorkSpaceId(workSpaceId);
        return projectService.saveProject(project);
    }

    @Override
    public WorkSpaceGeneralResponse editWorkSpace(String workSpaceId,WorkSpaceRequest workSpaceRequest) {
        WorkSpace workSpace = workSpaceRepository.findById(workSpaceId).orElseThrow(
                ()->new ResourceNotFound("WorkSpace not found!")
        );
        workSpace.setName(workSpaceRequest.getName());
        workSpace.setDescription(workSpaceRequest.getDescription());
        workSpaceRepository.save(workSpace);
        return getWorkSpaceGeneralResponse(workSpace);
    }

    @Override
    public void deleteWorkSpace(String workSpaceId) {
        if(workSpaceRepository.existsById(workSpaceId)){
            throw new ResourceNotFound("WorkSpace not found!");
        }
        workSpaceRepository.deleteById(workSpaceId);
    }

    @Override
    public boolean isWorkSpaceExist(String workSpaceId) {
        return workSpaceRepository.existsById(workSpaceId);
    }

    public WorkSpaceGeneralResponse getWorkSpaceGeneralResponse(WorkSpace workSpace){
        return modelMapper.map(workSpace, WorkSpaceGeneralResponse.class);
    }

    public WorkSpaceResponse getWorkSpaceResponse(WorkSpace workSpace){
        WorkSpaceResponse workSpaceResponse = modelMapper.map(workSpace, WorkSpaceResponse.class);
        workSpaceResponse.setProjects(projectService.getAllProjectByWorkSpace(workSpace.getId()));
        return workSpaceResponse;
    }
}
