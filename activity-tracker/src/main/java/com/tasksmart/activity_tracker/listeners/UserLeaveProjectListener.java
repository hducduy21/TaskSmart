package com.tasksmart.activity_tracker.listeners;

import com.tasksmart.activity_tracker.services.ActivityTrackerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(id = "",topics = {""})
public class UserLeaveProjectListener {
    private final ActivityTrackerService activityService;
}
