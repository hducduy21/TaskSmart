package com.tasksmart.sharedLibrary.dtos.responses;

import com.tasksmart.sharedLibrary.dtos.messages.UnsplashResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProjectTemplateResponse {
    private String id;

    private String name;

    private String backgroundColor;
    private UnsplashResponse backgroundUnsplash;

    private String description;

    private List<ListCardTemplateResponse> listCards;
}
