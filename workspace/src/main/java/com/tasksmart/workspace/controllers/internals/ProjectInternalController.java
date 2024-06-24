package com.tasksmart.workspace.controllers.internals;

import com.tasksmart.sharedLibrary.dtos.request.ProjectTemplateRequest;
import com.tasksmart.sharedLibrary.dtos.responses.ProjectTemplateResponse;
import com.tasksmart.sharedLibrary.dtos.responses.WorkSpaceGeneralResponse;
import com.tasksmart.workspace.services.ProjectInternalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * ProjectInternalController
 *
 * @version 1.0
 * @author Duy Hoang
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/${url_base}/internal/${url_project}")
public class ProjectInternalController {
    private final ProjectInternalService projectInternalService;

    @GetMapping("/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectTemplateResponse getProjectTemplate(@PathVariable String projectId){
        return projectInternalService.getProjectTemplate(projectId);
    }

    @PostMapping("apply")
    @ResponseStatus(HttpStatus.CREATED)
    public String applyTemplate(@RequestParam String projectId, @RequestParam String workspaceId){
        return projectInternalService.applyTemplate(projectId, workspaceId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ProjectTemplateResponse createProjectTemplate(@Valid @RequestBody ProjectTemplateRequest projectTemplateRequest){
        return projectInternalService.createProjectTemplate(projectTemplateRequest);
    }

//    @PutMapping("/{projectId}")
//    @ResponseStatus(HttpStatus.OK)
//    public ProjectTemplateResponse createProjectTemplate(@PathVariable String id, @Valid @RequestBody ProjectTemplateRequest projectTemplateRequest){
//        return projectInternalService.createProjectTemplate(projectTemplateRequest);
//    }
}
