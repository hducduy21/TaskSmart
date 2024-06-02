package com.tasksmart.workspace.controllers.internals;

import com.tasksmart.workspace.dtos.response.WorkSpaceResponse;
import com.tasksmart.workspace.services.WorkSpaceInternalService;
import com.tasksmart.workspace.services.WorkSpaceService;
import com.tasksmart.sharedLibrary.dtos.responses.WorkSpaceGeneralResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * WorkSpaceInternalController
 *
 * @version 1.0
 * @author Duy Hoang
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/${url_base}/internal/${url_workspace}")
public class WorkSpaceInternalController {
    private final WorkSpaceInternalService workSpaceInternalService;

    @PostMapping("personal")
    @ResponseStatus(HttpStatus.OK)
    public WorkSpaceGeneralResponse createrPersonalWorkspace(@RequestParam String userId, @RequestParam String name, @RequestParam String username){
        return workSpaceInternalService.createPersonalWorkSpace(userId, name, username);
    }
}
