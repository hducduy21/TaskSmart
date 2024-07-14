package com.tasksmart.resource.dtos.responses;

import com.tasksmart.sharedLibrary.dtos.messages.UnsplashResponse;
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
public class TemplateGeneralResponse {
    private String id;
    private String name;
    private String description;
    private int viewCount;
    private int useCount;
    private UnsplashResponse image;
    private CategoryResponse category;
    private Date createdDate;
}
