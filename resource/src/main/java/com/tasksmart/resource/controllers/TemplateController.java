package com.tasksmart.resource.controllers;

import com.tasksmart.resource.dtos.requests.TemplateRequest;
import com.tasksmart.resource.dtos.responses.ProjectApplyResponse;
import com.tasksmart.resource.dtos.responses.TemplateGeneralResponse;
import com.tasksmart.resource.dtos.responses.TemplateResponse;
import com.tasksmart.resource.services.TemplateService;
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
    public List<TemplateResponse> searchTemplate(@RequestParam String keyword){
        return templateService.searchTemplate(keyword);
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

    @GetMapping(
            value = "/img/{imageId}",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public byte[] getTemplateImage(@PathVariable String imageId){
        return templateService.getTemplateImage(imageId);
    }

    @PostMapping("{templateId}/img")
    @ResponseStatus(HttpStatus.CREATED)
    public TemplateResponse uploadImageTemplate(@PathVariable String templateId,@RequestPart MultipartFile image){
        return templateService.uploadTemplateImage(templateId, image);
    }

    @PutMapping("{templateId}/img")
    @ResponseStatus(HttpStatus.CREATED)
    public TemplateResponse changeImageTemplate(@PathVariable String templateId,@RequestPart MultipartFile image){
        return templateService.changeTemplateImage(templateId, image);
    }

    @PostMapping("{templateId}/apply")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectApplyResponse applyTemplate(@PathVariable String templateId, @RequestParam String workspaceId){
        return templateService.applyTemplate(templateId, workspaceId);
    }
}
