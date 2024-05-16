package com.example.workspace.controllers;

import com.example.workspace.dtos.request.ProjectRequest;
import com.example.workspace.dtos.response.ProjectGeneralResponse;
import com.example.workspace.dtos.response.ProjectResponse;
import com.example.workspace.services.ProjectService;
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
    public ProjectResponse getProjectById(String projectId){
        return projectService.getProjectById(projectId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectGeneralResponse createPersonalProject(){
        //wait to authenticate
        return null;
    }

    @PutMapping("{projectId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectGeneralResponse editProject(@PathVariable String projectId ,@RequestBody ProjectRequest projectRequest){
        return projectService.editProject(projectId, projectRequest);
    }

    public void deleteProject(){

    }
}
