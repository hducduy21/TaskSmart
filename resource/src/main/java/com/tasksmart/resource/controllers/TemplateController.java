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

/**
 * This class is the controller for the templates.
 * It handles the requests related to the templates.
 *
 * @author Duy Hoang
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/${url_base}/${url_template}")
@Slf4j
public class TemplateController {
    /**
     * The service for the templates.
     */
    private final TemplateService templateService;

    /**
     * Get all templates.
     *
     * @param categoryId The id of the category
     * @param category The name of the category
     * @return The list of all templates
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TemplateGeneralResponse> getAllTemplate(@RequestParam(required = false) String categoryId,
                                                        @RequestParam(required = false) String category){
        return templateService.getALlTemplates(categoryId, category);
    }

    /**
     * Get a template by its id.
     *
     * @param templateId The id of the template
     * @return The template
     */
    @GetMapping("{templateId}")
    @ResponseStatus(HttpStatus.OK)
    public TemplateResponse getTemplateById(@PathVariable String templateId){
        return templateService.getTemplateById(templateId);
    }

    /**
     * Get a template image by its id.
     *
     * @param query The id of the template
     * @return The image
     */
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public SearchAllResponse searchTemplate(@RequestParam String query){
        return templateService.searchTemplate(query);
    }

    /**
     * Get a template image by its id.
     *
     * @param query The id of the template
     * @return The image
     */
    @GetMapping("/search-only")
    @ResponseStatus(HttpStatus.OK)
    public List<TemplateGeneralResponse> searchTemplatesOnly(@RequestParam String query){
        return templateService.searchTemplatesOnly(query);
    }

    /**
     * Get a template image by its id.
     *
     * @param templateRequest The id of the template
     * @return The image
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TemplateResponse createTemplate(@Valid @RequestBody TemplateRequest templateRequest){
        return templateService.createTemplate(templateRequest);
    }

    /**
     * Get a template image by its id.
     * @param templateId
     * @param templateRequest
     * @return
     */
    @PutMapping("/{templateId}")
    @ResponseStatus(HttpStatus.CREATED)
    public TemplateResponse updateTemplate(@PathVariable String templateId, @Valid @RequestBody TemplateRequest templateRequest){
        return templateService.updateTemplate(templateId, templateRequest);
    }

    /**
     * Get a template image by its id.
     * @param templateId
     * @param workspaceId
     * @param projectName
     * @return ProjectApplyResponse
     */
    @PostMapping("{templateId}/apply")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectApplyResponse applyTemplate(@PathVariable String templateId, @RequestParam String workspaceId, @RequestParam String projectName){
        return templateService.applyTemplate(templateId, workspaceId, projectName);
    }

    /**
     * Get a template image by its id.
     * @param templateId
     * @return ProjectApplyResponse
     */
    @PutMapping("{templateId}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disableTemplate(@PathVariable String templateId){
        templateService.disableTemplate(templateId);
    }
}
