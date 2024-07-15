package com.tasksmart.resource.services;

import com.tasksmart.resource.dtos.requests.TemplateRequest;
import com.tasksmart.resource.dtos.responses.ProjectApplyResponse;
import com.tasksmart.resource.dtos.responses.TemplateGeneralResponse;
import com.tasksmart.resource.dtos.responses.TemplateResponse;
import com.tasksmart.sharedLibrary.dtos.responses.SearchAllResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TemplateService {
    List<TemplateGeneralResponse> getALlTemplates(String categoryId, String category);
    SearchAllResponse searchTemplate(String keyword);
    TemplateResponse getTemplateById(String id);
    TemplateResponse createTemplate(TemplateRequest templateRequest);
    TemplateResponse updateTemplate(String id, TemplateRequest templateRequest);
    void disableTemplate(String id);
    void enableTemplate(String id);
    void deleteTemplate(String id);

    List<TemplateResponse> getDisableTemplate();

    ProjectApplyResponse applyTemplate(String templateId, String workspaceId, String projectName);
}
