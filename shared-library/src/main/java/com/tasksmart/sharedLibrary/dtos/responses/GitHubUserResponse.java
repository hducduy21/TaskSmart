package com.tasksmart.sharedLibrary.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GitHubUserResponse {
    private String login;
    private String name;
    private String avatar_url;
}
