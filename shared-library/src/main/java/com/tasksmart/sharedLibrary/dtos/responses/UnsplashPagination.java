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
public class UnsplashPagination {
    private int total;
    private int total_pages;
    private List<UnsplashResponse> results;
}
