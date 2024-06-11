package com.tasksmart.resource.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("avatars")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Avatar {
    @Id
    private String id;
    private String fileName;
}
