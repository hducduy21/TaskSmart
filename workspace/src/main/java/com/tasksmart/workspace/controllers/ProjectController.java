package com.tasksmart.workspace.controllers;

import com.tasksmart.sharedLibrary.dtos.request.DBRagRequest;
import com.tasksmart.sharedLibrary.dtos.request.RagUriRequest;
import com.tasksmart.sharedLibrary.dtos.request.URIRequest;
import com.tasksmart.sharedLibrary.dtos.responses.*;
import com.tasksmart.workspace.dtos.request.*;
import com.tasksmart.workspace.dtos.response.*;
import com.tasksmart.workspace.services.ProjectService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/${url_base}/${url_project}")
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/recent")
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectGeneralResponse> getAllProjectRecent(){
        return projectService.getRecentProjects();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectGeneralResponse> getAllProject(){
        return projectService.getAllProject();
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectGeneralResponse> searchProject(@RequestParam String query, @RequestParam(required = false) String workSpaceId){
        return projectService.searchProject(query, workSpaceId);
    }

    @GetMapping("/invite/{projectId}/{inviteCode}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectResponse joinProject(@PathVariable String projectId, @PathVariable String inviteCode){
        return projectService.joinProjectByInviteCode(projectId, inviteCode);
    }

    @PatchMapping("/invite/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public InviteCodeResponse updateInviteCode(@PathVariable String projectId,
                                                @RequestParam(required = false) Boolean isPublic,
                                                @RequestParam(required = false) Boolean refresh){
        return projectService.updateInviteCode(projectId, isPublic, refresh);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectGeneralResponse createPersonalProject(@Valid @RequestBody ProjectRequest projectRequest){
        return projectService.createProject(projectRequest);
    }

    @GetMapping("{projectId}/assets/{assetId}")
    @ResponseStatus(HttpStatus.OK)
    public byte[] viewProjectImageAssets(@PathVariable String projectId, @PathVariable String assetId){
        return projectService.viewImage(projectId,assetId);
    }

    @GetMapping("{projectId}/document")
    @ResponseStatus(HttpStatus.OK)
    public byte[] getProjectDocument(@PathVariable String projectId){
        return projectService.getProjectDocument(projectId);
    }

    @PostMapping("{projectId}/document")
    @ResponseStatus(HttpStatus.OK)
    public String putProjectDocument(@PathVariable String projectId, @RequestPart MultipartFile file){
        return projectService.putProjectDocument(projectId,file);
    }

    @GetMapping("{projectId}/generate-task")
    @ResponseStatus(HttpStatus.OK)
    public TaskGenResponse generateTask(@PathVariable String projectId){
        return projectService.generateTask(projectId);
    }

    @PutMapping("{projectId}/background")
    @ResponseStatus(HttpStatus.OK)
    public ProjectResponse changeBackground(@PathVariable String projectId, @RequestParam String background){
        return projectService.updateBackground(projectId, background);
    }

    @PostMapping("{projectId}/apply-generate")
    @ResponseStatus(HttpStatus.OK)
    public ProjectResponse applyProjectGenerate(@PathVariable String projectId, @Valid @RequestBody TaskGenerateRequest taskGenerateRequest){
        return projectService.applyProjectGenerate(projectId,taskGenerateRequest);
    }

    @GetMapping("{projectId}/get-db-structure")
    @ResponseStatus(HttpStatus.OK)
    public StatementResponse getDBStructure(@PathVariable String projectId){
        return projectService.getDBStructure(projectId);
    }

    @PostMapping("{projectId}/save-db-structure")
    @ResponseStatus(HttpStatus.OK)
    public void saveDbStructure(@PathVariable String projectId, @Valid @RequestBody DBStructureRequest dbStructureRequest){
        projectService.saveDbStructure(projectId,dbStructureRequest);
    }

    @PostMapping("{projectId}/get-structure-by-uri")
    @ResponseStatus(HttpStatus.OK)
    public DatabaseConnectResponse connectDb(@PathVariable String projectId, @Valid @RequestBody URIRequest uriRequest){
        return projectService.connectSQLDB(projectId,uriRequest);
    }

    @PostMapping("{projectId}/database-rag-by-uri")
    @ResponseStatus(HttpStatus.OK)
    public StatementRunableResponse connectDb(@PathVariable String projectId, @Valid @RequestBody RagUriRequest ragUriRequest){
        return projectService.DBRagByURI(projectId,ragUriRequest);
    }

    @GetMapping("{projectId}/generate-structure")
    @ResponseStatus(HttpStatus.OK)
    public StatementResponse generateDBStructure(@PathVariable String projectId, @RequestParam String database){
        return projectService.generateDBStructure(projectId, database);
    }

    @PostMapping("{projectId}/database-rag")
    @ResponseStatus(HttpStatus.OK)
    public DatabaseStatementResponse databaseRAG(@PathVariable String projectId, @RequestBody DBRagRequest dbRagRequest){
        return projectService.databaseRAG(projectId,dbRagRequest);
    }

    @PostMapping("{projectId}/move/listcard")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponse moveListCard(@PathVariable String projectId,
                                           @Valid @RequestBody MoveListCardRequest moveListCardRequest){
        return projectService.moveListCard(projectId, moveListCardRequest);
    }

    @PostMapping("{projectId}/move/card")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponse moveCard(@PathVariable String projectId,
                                        @Valid @RequestBody MoveCardRequest moveCardRequest){
        return projectService.moveCard(projectId, moveCardRequest);
    }

    @PutMapping("{projectId}/{listCardId}")
    @ResponseStatus(HttpStatus.OK)
    public ListCardResponse updateListCard(@PathVariable String projectId,
                                           @PathVariable String listCardId,
                                           @Valid @RequestBody ListCardCreationRequest listCardCreationRequest){
        return projectService.updateListCard(projectId, listCardId, listCardCreationRequest);
    }

    @DeleteMapping("{projectId}/{listCardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteListCard(@PathVariable String projectId,
                                           @PathVariable String listCardId){
        projectService.deleteListCard(projectId, listCardId);
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

    @GetMapping("{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectResponse getProjectById(@PathVariable String projectId){
        return projectService.getProjectById(projectId);
    }
}
