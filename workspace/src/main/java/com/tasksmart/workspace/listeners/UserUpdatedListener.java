package com.tasksmart.workspace.listeners;

import com.tasksmart.workspace.services.ProjectInternalService;
import com.tasksmart.workspace.services.WorkSpaceInternalService;
import com.tasksmart.sharedLibrary.dtos.messages.UserMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * This class is the listener for the User Updated message.
 * This class listens to the User Updated message and updates the user in the workspace and project.
 * This class is used to listen to the User Updated message.
 *
 * @author Duy Hoang
 */
@Component
@RequiredArgsConstructor
@KafkaListener(id = "user-updation-group",topics = {"user-updation"})
public class UserUpdatedListener {
    private final WorkSpaceInternalService workSpaceInternalService;
    private final ProjectInternalService projectInternalService;

    /**
     *
     * @param userMessage
     */
    @KafkaHandler
    public void handleUserMessageUpdatedMessage(UserMessage userMessage) {
        workSpaceInternalService.updateUsersInWorkSpace(userMessage);
        projectInternalService.updateUsersInProject(userMessage);
    }

}
