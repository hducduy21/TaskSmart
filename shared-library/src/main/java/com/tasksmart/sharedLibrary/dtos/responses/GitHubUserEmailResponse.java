package com.tasksmart.sharedLibrary.dtos.responses;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GitHubUserEmailResponse {
    private String email;
    private boolean primary;
}
