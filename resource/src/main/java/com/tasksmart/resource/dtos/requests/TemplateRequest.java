package com.tasksmart.resource.dtos.requests;

import com.tasksmart.sharedLibrary.dtos.request.ProjectTemplateRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TemplateRequest {
    private String name;
    private String description;
    private ProjectTemplateRequest project;
    private String categoryId;
}
