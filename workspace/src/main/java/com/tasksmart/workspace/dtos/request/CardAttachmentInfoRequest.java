package com.tasksmart.workspace.dtos.request;

import com.tasksmart.sharedLibrary.models.EFileType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CardAttachmentInfoRequest {
    public String fileId;
    public String title;
    private String description;
}
