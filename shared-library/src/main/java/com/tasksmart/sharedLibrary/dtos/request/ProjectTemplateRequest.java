package com.tasksmart.sharedLibrary.dtos.request;

import com.tasksmart.sharedLibrary.dtos.messages.UnsplashResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProjectTemplateRequest {
    /** The name of the Project. */
    @NotBlank(message = "Project name is required")
    private String name;

    /** The background of the Project. */
    private UnsplashResponse backgroundUnsplash;

    /** The description of the Project. */
    private String description;

    @NotEmpty(message = "List of card is required")
    private List<ListCardTemplateRequest> listCards;
}
