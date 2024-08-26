package com.tasksmart.workspace.controllers;

import com.tasksmart.sharedLibrary.dtos.responses.SearchAllResponse;
import com.tasksmart.workspace.dtos.request.MembersAdditionalRequest;
import com.tasksmart.workspace.dtos.request.ProjectRequest;
import com.tasksmart.workspace.dtos.request.WorkSpaceRequest;
import com.tasksmart.workspace.dtos.request.WorkspaceUpdateImage;
import com.tasksmart.workspace.dtos.response.*;
import com.tasksmart.workspace.services.WorkSpaceService;
import com.tasksmart.sharedLibrary.configs.AppConstant;
import com.tasksmart.sharedLibrary.dtos.responses.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is the controller for the Work Space.
 *
 * @author Duy Hoang
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/${url_base}/${url_workspace}")
public class WorkSpaceController {
    private final WorkSpaceService workSpaceService;

    /** {@inheritDoc} */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<WorkSpaceGeneralResponse> getAllWorkSpace(){
        return workSpaceService.getAllWorkSpace();
    }

    /** {@inheritDoc} */
    @GetMapping("search")
    @ResponseStatus(HttpStatus.OK)
    public SearchAllResponse search(@RequestParam String query){
        return workSpaceService.search(query);
    }

    /** {@inheritDoc} */
    @GetMapping("/invite/{projectId}/{inviteCode}")
    @ResponseStatus(HttpStatus.OK)
    public WorkSpaceResponse joinWorkSpace(@PathVariable String workspaceId, @PathVariable String inviteCode){
        return workSpaceService.joinWorkSpaceByInviteCode(workspaceId, inviteCode);
    }

    /** {@inheritDoc} */
    @PatchMapping("/invite/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public InviteCodeResponse updateInviteCode(@PathVariable String workspaceId,
                                               @RequestParam(required = false) Boolean isPublic,
                                               @RequestParam(required = false) Boolean refresh){
        return workSpaceService.updateInviteCode(workspaceId, isPublic, refresh);
    }

    /** {@inheritDoc} */
    @GetMapping("{workSpaceId}")
    @ResponseStatus(HttpStatus.OK)
    public WorkSpaceResponse getWorkSpaceById(@PathVariable String workSpaceId){
        return workSpaceService.getWorkSpaceById(workSpaceId);
    }

    /** {@inheritDoc} */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkSpaceGeneralResponse createWorkSpace(@Valid @RequestBody WorkSpaceRequest workSpaceRequest){
        return  workSpaceService.createWorkSpace(workSpaceRequest);
    }

    /** {@inheritDoc} */
    @PutMapping("{workSpaceId}/background")
    @ResponseStatus(HttpStatus.OK)
    public WorkSpaceGeneralResponse updateBackgroundWorkSpace(@PathVariable String workSpaceId, @RequestBody WorkspaceUpdateImage image){
        return  workSpaceService.setUnsplashBackground(workSpaceId, image);
    }

//    @PostMapping("{workSpaceId}/members")
//    @ResponseStatus(HttpStatus.CREATED)
//    public SuccessResponse addMembers(@PathVariable String workSpaceId, @Valid @RequestBody MembersAdditionalRequest membersAdditionalRequest){
//        return workSpaceService.inviteMembers(workSpaceId, membersAdditionalRequest);
//    }

    /** {@inheritDoc} */
    @PostMapping("{workSpaceId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectGeneralResponse createProject(@Valid @PathVariable String workSpaceId, @RequestBody ProjectRequest projectRequest){
        return workSpaceService.createProject(workSpaceId, projectRequest);
    }

    /** {@inheritDoc} */
    @PutMapping("{workSpaceId}")
    @ResponseStatus(HttpStatus.OK)
    public WorkSpaceGeneralResponse editWorkSpace(@PathVariable String workSpaceId, @RequestBody WorkSpaceRequest workSpaceRequest){
        return workSpaceService.editWorkSpace(workSpaceId, workSpaceRequest);
    }

    /** {@inheritDoc} */
    @DeleteMapping("{workSpaceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWorkSpace(@PathVariable String workSpaceId){
        workSpaceService.deleteWorkSpace(workSpaceId);
    }
}
