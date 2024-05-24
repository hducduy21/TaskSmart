package com.example.workspace.controllers;

import com.example.workspace.dtos.request.ProjectRequest;
import com.example.workspace.dtos.request.WorkSpaceRequest;
import com.example.workspace.dtos.response.ProjectGeneralResponse;
import com.example.workspace.dtos.response.WorkSpaceGeneralResponse;
import com.example.workspace.dtos.response.WorkSpaceResponse;
import com.example.workspace.services.WorkSpaceService;
import com.tasksmart.sharedLibrary.configs.AppConstant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/${url_base}/${url_workspace}")
public class WorkSpaceController {
    private final WorkSpaceService workSpaceService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<WorkSpaceGeneralResponse> getAllWorkSpace(){
        System.out.println(AppConstant.CHECK);
        return workSpaceService.getAllWorkSpace();
    }

    @GetMapping("{workSpaceId}")
    @ResponseStatus(HttpStatus.OK)
    public WorkSpaceResponse getWorkSpaceById(@PathVariable String workSpaceId){
        return workSpaceService.getWorkSpaceById(workSpaceId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkSpaceGeneralResponse createWorkSpace(@Valid @RequestBody WorkSpaceRequest workSpaceRequest){
        return  workSpaceService.createWorkSpace(workSpaceRequest);
    }

    @PostMapping("{workSpaceId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectGeneralResponse createProject(@Valid @PathVariable String workSpaceId, @RequestBody ProjectRequest projectRequest){
        return workSpaceService.createProject(workSpaceId, projectRequest);
    }

    @PutMapping("{workSpaceId}")
    @ResponseStatus(HttpStatus.OK)
    public WorkSpaceGeneralResponse editWorkSpace(@PathVariable String workSpaceId, @RequestBody WorkSpaceRequest workSpaceRequest){
        return workSpaceService.editWorkSpace(workSpaceId, workSpaceRequest);
    }

    @DeleteMapping("{workSpaceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWorkSpace(@PathVariable String workSpaceId){
        workSpaceService.deleteWorkSpace(workSpaceId);
    }
}
