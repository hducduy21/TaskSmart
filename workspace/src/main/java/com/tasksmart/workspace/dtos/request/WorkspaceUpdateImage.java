package com.tasksmart.workspace.dtos.request;

import com.tasksmart.sharedLibrary.dtos.messages.UnsplashResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorkspaceUpdateImage {
    private UnsplashResponse backgroundUnsplash;
}
