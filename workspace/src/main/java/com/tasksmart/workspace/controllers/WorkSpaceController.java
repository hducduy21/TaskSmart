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

@RestController
@RequiredArgsConstructor
@RequestMapping("/${url_base}/${url_workspace}")
public class WorkSpaceController {
    private final WorkSpaceService workSpaceService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<WorkSpaceGeneralResponse> getAllWorkSpace(){
        return workSpaceService.getAllWorkSpace();
    }

    @GetMapping("search")
    @ResponseStatus(HttpStatus.OK)
    public SearchAllResponse search(@RequestParam String query){
        return workSpaceService.search(query);
    }

    @GetMapping("/invite/{workspaceId}/{inviteCode}")
    @ResponseStatus(HttpStatus.OK)
    public WorkSpaceResponse joinWorkSpace(@PathVariable String workspaceId, @PathVariable String inviteCode){
        return workSpaceService.joinWorkSpaceByInviteCode(workspaceId, inviteCode);
    }

    @PatchMapping("/invite/{workspaceId}")
    @ResponseStatus(HttpStatus.OK)
    public InviteCodeResponse updateInviteCode(@PathVariable String workspaceId,
                                               @RequestParam(required = false) Boolean isPublic,
                                               @RequestParam(required = false) Boolean refresh){
        return workSpaceService.updateInviteCode(workspaceId, isPublic, refresh);
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
