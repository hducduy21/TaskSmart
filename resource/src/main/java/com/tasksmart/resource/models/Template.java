package com.tasksmart.resource.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Template {
    @Id
    private String id;
    private String name;
    private String imageId;
    private String categoryId;
    private String projectId;
}
