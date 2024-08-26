package com.tasksmart.resource.models;

import com.tasksmart.sharedLibrary.dtos.messages.UnsplashResponse;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("templates")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Template {
    @Id
    private String id;
    private String name;
    private String description;
    private int viewCount;
    private int useCount;
    private UnsplashResponse image;
    private String categoryId;
    private String projectId;

    private boolean enabled;
    @Builder.Default
    private Date createdDate = new Date();
}
