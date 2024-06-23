package com.tasksmart.resource.services;

import com.tasksmart.resource.dtos.requests.TemplateRequest;
import com.tasksmart.resource.dtos.responses.ProjectApplyResponse;
import com.tasksmart.resource.dtos.responses.TemplateGeneralResponse;
import com.tasksmart.resource.dtos.responses.TemplateResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TemplateService {
    List<TemplateGeneralResponse> getALlTemplates(String categoryId, String category);
    List<TemplateResponse> getDisableTemplate();
    TemplateResponse getTemplateById(String id);
    TemplateResponse createTemplate(TemplateRequest templateRequest);
    TemplateResponse updateTemplate(String id, TemplateRequest templateRequest);
    void disableTemplate(String id);
    void enableTemplate(String id);
    void deleteTemplate(String id);

    TemplateResponse uploadTemplateImage(String templateId ,MultipartFile image);

    byte[] getTemplateImage(String templateId);

    TemplateResponse changeTemplateImage(String templateId, MultipartFile image);

    ProjectApplyResponse applyTemplate(String templateId, String workspaceId);
}
