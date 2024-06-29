package com.tasksmart.activity_tracker.listeners;

import com.tasksmart.activity_tracker.services.ActivityTrackerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(id = "",topics = {""})
public class CardUpdationListener {
    private final ActivityTrackerService activityService;
}
