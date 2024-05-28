package com.example.workspace.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MembersAdditionalRequest {
    @NotEmpty(message = "The userIds list must not be empty")
    private List<String> UserIds;
}
