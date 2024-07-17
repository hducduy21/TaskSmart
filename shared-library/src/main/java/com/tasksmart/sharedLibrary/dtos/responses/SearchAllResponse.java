package com.tasksmart.sharedLibrary.dtos.responses;

import com.tasksmart.sharedLibrary.dtos.messages.UnsplashResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SearchAllResponse {
    @Builder.Default
    private List<WorkSpaceResponse> workspaces = new ArrayList<>();

    @Builder.Default
    private List<ProjectResponse> projects = new ArrayList<>();

    @Builder.Default
    private List<ListCardResponse> listCards = new ArrayList<>();

    @Builder.Default
    private List<CardResponse> cards = new ArrayList<>();

    @Builder.Default
    private List<TemplateResponse> templates = new ArrayList<>();

    @Builder.Default
    private List<NoteResponse> notes= new ArrayList<>();

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class WorkSpaceResponse {
        private String id;
        private String name;
        private UnsplashResponse backgroundUnsplash;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class ProjectResponse {
        private String id;
        private String name;
        private String backgroundColor;
        private UnsplashResponse backgroundUnsplash;
        private WorkSpaceResponse workspace;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class ListCardResponse {
        private String id;
        private String name;
        private ProjectResponse project;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class CardResponse {
        private String id;
        private String name;
        private ProjectResponse project;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class TemplateResponse {
        private String id;
        private String name;
        private UnsplashResponse image;
        private CategoryResponse category;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class NoteResponse{
        private String id;
        private String title;
        private String content;
    }
}
