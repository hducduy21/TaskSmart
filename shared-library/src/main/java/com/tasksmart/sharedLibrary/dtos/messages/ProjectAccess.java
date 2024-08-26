package com.tasksmart.sharedLibrary.dtos.messages;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProjectAccess {
    private String userId;

    private String projectId;

    private String projectName;
}
