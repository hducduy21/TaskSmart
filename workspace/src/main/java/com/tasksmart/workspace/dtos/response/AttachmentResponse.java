package com.tasksmart.workspace.dtos.response;

import com.tasksmart.sharedLibrary.models.EFileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AttachmentResponse {
    private String fileId;

    private String title;

    private EFileType type;

    private String description;
}
