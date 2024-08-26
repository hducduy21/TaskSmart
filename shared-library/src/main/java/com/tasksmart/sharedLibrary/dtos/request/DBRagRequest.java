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
public class DBRagRequest {
    public String database;

    @NotBlank(message = "Please provide a database structure for the query.")
    public String context;

    @NotBlank(message = "Please enter question.")
    public String question;
}
