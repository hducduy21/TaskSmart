package com.tasksmart.resource.dtos.responses;

import com.tasksmart.sharedLibrary.dtos.responses.ProjectTemplateResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TemplateResponse {
    private String id;
    private String name;
    private String description;
    private int viewCount;
    private int useCount;
    private String imageUrl;
    private CategoryResponse category;
    private ProjectTemplateResponse project;
    private Date createdDate;
}