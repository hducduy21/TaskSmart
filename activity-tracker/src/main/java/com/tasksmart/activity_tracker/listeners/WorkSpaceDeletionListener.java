package com.tasksmart.activity_tracker.listeners;

import com.tasksmart.activity_tracker.services.ActivityTrackerService;
import com.tasksmart.sharedLibrary.dtos.messages.WorkSpaceMessage;
import com.tasksmart.sharedLibrary.models.enums.EActivityType;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(id = "",topics = {""})
public class WorkSpaceDeletionListener {
    private final ActivityTrackerService activityService;

    @KafkaHandler
    public void handleWorkSpaceCreation(WorkSpaceMessage workSpaceMessage) {
        activityService.deleteWorkSpaceActivity(workSpaceMessage);
    }
}
