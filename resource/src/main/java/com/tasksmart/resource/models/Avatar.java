package com.tasksmart.resource.models;

import com.tasksmart.sharedLibrary.models.enums.EGender;
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
    private EGender gender;
    private String imageId;
}
