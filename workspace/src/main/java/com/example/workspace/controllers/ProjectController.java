package com.example.workspace.controllers;

import com.example.workspace.dtos.request.CardCreationRequest;
import com.example.workspace.dtos.request.ListCardCreationRequest;
import com.example.workspace.dtos.request.ProjectRequest;
import com.example.workspace.dtos.response.CardResponse;
import com.example.workspace.dtos.response.ListCardResponse;
import com.example.workspace.dtos.response.ProjectGeneralResponse;
import com.example.workspace.dtos.response.ProjectResponse;
import com.example.workspace.services.ProjectService;
import com.tasksmart.sharedLibrary.configs.AppConstant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/${url_base}/${url_project}")
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/recent")
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectGeneralResponse> getAllProjectRecent(){
        return null;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectGeneralResponse> getAllProject(){
        return projectService.getAllProject();
    }

    @GetMapping("{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectResponse getProjectById(@PathVariable String projectId){
        return projectService.getProjectById(projectId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectGeneralResponse createPersonalProject(){
        //wait to authenticate
        return null;
    }

    @PutMapping("{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectGeneralResponse editProject(@PathVariable String projectId,
                                              @Valid @RequestBody ProjectRequest projectRequest){
        return projectService.editProject(projectId, projectRequest);
    }

    @PostMapping("{projectId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ListCardResponse createListCard(@PathVariable String projectId,
                                           @Valid @RequestBody ListCardCreationRequest listCardCreationRequest){
        return projectService.createListCard(projectId, listCardCreationRequest);
    }

    @PostMapping("{projectId}/{listCardId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CardResponse createCard(@PathVariable String projectId,
                                   @PathVariable String listCardId,
                                   @Valid @RequestBody CardCreationRequest cardCreationRequest){
        return projectService.createCard(projectId, listCardId, cardCreationRequest);
    }

    @DeleteMapping("{projectId}")
    public void deleteProject(@PathVariable String projectId){
        projectService.deleteProject(projectId);
    }
}
