package com.tasksmart.activity_tracker.controllers;

import com.tasksmart.activity_tracker.services.ActivityTrackerService;
import com.tasksmart.sharedLibrary.dtos.responses.ProjectAccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for activity tracker endpoints
 *
 * @author Duy Hoang
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/${url_base}/${url_activity}")
@Slf4j
public class ActivityTrackerController {
    private final ActivityTrackerService activityTrackerService;

    /**
     * Get recent projects
     *
     * @return List of ProjectAccessResponse
     */
    @GetMapping("/recent/projects")
    public List<ProjectAccessResponse> getProjectRecent(){
        return activityTrackerService.getProjectRecent();
    }
}
