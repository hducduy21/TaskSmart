package com.tasksmart.sharedLibrary.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ListCardTemplateRequest {
    @NotBlank(message = "List name is required")
    private String name;

    private List<CardTemplateRequest> cards;
}
