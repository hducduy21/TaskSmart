package com.example.user.services;

import com.example.user.dtos.response.UserGeneralResponse;
import com.tasksmart.sharedLibrary.dtos.messages.ProjectMessage;
import com.tasksmart.sharedLibrary.dtos.messages.UserJoinProjectMessage;
import com.tasksmart.sharedLibrary.dtos.messages.UserJoinWorkSpaceMessage;
import com.tasksmart.sharedLibrary.dtos.messages.WorkSpaceMessage;

import java.util.List;

public interface UserInternalService {
    UserGeneralResponse getUserGeneralById(String id);
    List<UserGeneralResponse> getUsersGeneralByListId(List<String> userIds);

    void createWorkSpace(WorkSpaceMessage workSpaceMessage);
    void updateWorkSpace(WorkSpaceMessage workSpaceMessage);
    void joinWorkSpace(UserJoinWorkSpaceMessage userJoinWorkSpaceMessage);
    void createProject(ProjectMessage projectMessage);
    void updateProject(ProjectMessage projectMessage);
    void joinProject(UserJoinProjectMessage userJoinProjectMessage);
}
