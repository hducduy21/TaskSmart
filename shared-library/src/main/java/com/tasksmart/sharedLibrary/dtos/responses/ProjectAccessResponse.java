package com.tasksmart.sharedLibrary.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProjectAccessResponse {
    private String id;
    private LocalDateTime updatedAt;
}
