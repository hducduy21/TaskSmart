package com.tasksmart.sharedLibrary.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class URIRequest {
    @NotBlank(message = "Please provide a URI.")
    private String uri;
}
