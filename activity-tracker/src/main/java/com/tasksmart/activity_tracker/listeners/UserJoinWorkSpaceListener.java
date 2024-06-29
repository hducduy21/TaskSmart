package com.tasksmart.activity_tracker.listeners;

import com.tasksmart.activity_tracker.services.ActivityTrackerService;
import com.tasksmart.sharedLibrary.dtos.messages.UserJoinWorkSpaceMessage;
import com.tasksmart.sharedLibrary.models.enums.EActivityType;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(id = "",topics = {""})
public class UserJoinWorkSpaceListener {
    private final ActivityTrackerService activityService;

    @KafkaHandler
    public void handleUserJoinWorkSpace(UserJoinWorkSpaceMessage userJoinWorkSpaceMessage) {
        activityService.createWorkSpaceActivity(
                userJoinWorkSpaceMessage.getUserId(),
                userJoinWorkSpaceMessage.getId(),
                EActivityType.CREATE_WORKSPACE);
    }
}
