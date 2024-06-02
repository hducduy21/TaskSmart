package com.tasksmart.workspace.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Invitation {
    private boolean isPublic;
    private String code;
}
