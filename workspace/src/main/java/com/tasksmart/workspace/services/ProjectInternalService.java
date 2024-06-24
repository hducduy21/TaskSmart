package com.tasksmart.workspace.services;

import com.tasksmart.sharedLibrary.dtos.request.ProjectTemplateRequest;
import com.tasksmart.sharedLibrary.dtos.responses.ProjectTemplateResponse;

public interface ProjectInternalService {
    ProjectTemplateResponse getProjectTemplate(String projectId);
    ProjectTemplateResponse createProjectTemplate(ProjectTemplateRequest projectTemplateRequest);
    ProjectTemplateResponse updateProjectTemplate(String projectId, ProjectTemplateRequest projectTemplateRequest);

    String applyTemplate(String projectId, String workspaceId);
}
