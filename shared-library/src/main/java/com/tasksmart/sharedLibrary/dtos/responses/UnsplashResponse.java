package com.tasksmart.sharedLibrary.dtos.responses;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UnsplashResponse {
    private String id;
    private String color;
    private UnsplashUrls urls;

    @Getter
    @Setter
    public static class UnsplashUrls{
        private String raw;
        private String full;
        private String regular;
        private String small;
        private String thumb;
    }
}
