package com.tasksmart.resource.controllers;

import com.tasksmart.resource.dtos.requests.TemplateRequest;
import com.tasksmart.resource.dtos.responses.ProjectApplyResponse;
import com.tasksmart.resource.dtos.responses.TemplateGeneralResponse;
import com.tasksmart.resource.dtos.responses.TemplateResponse;
import com.tasksmart.resource.services.TemplateService;
import com.tasksmart.sharedLibrary.dtos.responses.SearchAllResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/${url_base}/${url_template}")
@Slf4j
public class TemplateController {
    private final TemplateService templateService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TemplateGeneralResponse> getAllTemplate(@RequestParam(required = false) String categoryId,
                                                        @RequestParam(required = false) String category){
        return templateService.getALlTemplates(categoryId, category);
    }

    @GetMapping("{templateId}")
    @ResponseStatus(HttpStatus.OK)
    public TemplateResponse getTemplateById(@PathVariable String templateId){
        return templateService.getTemplateById(templateId);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public SearchAllResponse searchTemplate(@RequestParam String query){
        return templateService.searchTemplate(query);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TemplateResponse createTemplate(@Valid @RequestBody TemplateRequest templateRequest){
        return templateService.createTemplate(templateRequest);
    }

    @PutMapping("/{templateId}")
    @ResponseStatus(HttpStatus.CREATED)
    public TemplateResponse updateTemplate(@PathVariable String templateId, @Valid @RequestBody TemplateRequest templateRequest){
        return templateService.updateTemplate(templateId, templateRequest);
    }

    @PostMapping("{templateId}/apply")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectApplyResponse applyTemplate(@PathVariable String templateId, @RequestParam String workspaceId){
        return templateService.applyTemplate(templateId, workspaceId);
    }

    @PutMapping("{templateId}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disableTemplate(@PathVariable String templateId){
        templateService.disableTemplate(templateId);
    }
}
