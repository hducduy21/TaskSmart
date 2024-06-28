package com.tasksmart.user.services.impls;

import com.tasksmart.user.models.User;
import com.tasksmart.user.repositories.UserRepository;
import com.tasksmart.user.services.UserInternalService;
import com.tasksmart.sharedLibrary.dtos.messages.*;
import com.tasksmart.sharedLibrary.dtos.responses.UserGeneralResponse;
import com.tasksmart.sharedLibrary.exceptions.ResourceNotFound;
import com.tasksmart.sharedLibrary.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserInternalServiceImpl implements UserInternalService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final AuthenticationUtils authenticationUtils;

    @Override
    public UserGeneralResponse getUserGeneralById(String id) {
        return userRepository.findById(id)
                .map(this::getUserGeneralResponse)
                .orElseThrow(() -> new ResourceNotFound("User not found!"));
    }

    @Override
    public List<UserGeneralResponse> getUsersGeneralByListId(List<String> userIds) {
        List<User> users = userRepository.findAllByIdIn(userIds);
        return users.stream().map(this::getUserGeneralResponse).toList();
    }

    @Override
    public void createWorkSpace(WorkSpaceMessage workSpaceMessage) {
        Optional<UserRelation> userRelation = workSpaceMessage.getUsers().stream()
                .filter(user -> user.getRole().equals("Owner"))
                .findFirst();

        if(userRelation.isEmpty()){
            log.error("Owner not found in workspace-{}", workSpaceMessage.getId());
            return;
        }

        String userId = userRelation.get().getUserId();

        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            User.WorkSpace workSpace = User.WorkSpace.builder()
                    .id(workSpaceMessage.getId())
                    .name(workSpaceMessage.getName())
                    .build();

            user.addWorkSpace(workSpace);
            userRepository.save(user);

            log.info("User-{} created workspace-{}",userId, workSpaceMessage.getId());
            return;
        }
        log.error("User-{} not found to create workspace-{}",userId, workSpaceMessage.getId());
    }

    @Override
    public void createProject(ProjectMessage projectMessage) {
        Optional<UserRelation> userRelation = projectMessage.getUsers().stream()
                .filter(user -> user.getRole().equals("Owner"))
                .findFirst();

        if(userRelation.isEmpty()){
            log.error("Owner not found in workspace-{}", projectMessage.getId());
            return;
        }
        String userId = userRelation.get().getUserId();

        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            User.Project project = User.Project.builder()
                    .id(projectMessage.getId())
                    .name(projectMessage.getName())
                    .backgroundColor(projectMessage.getBackgroundColor())
                    .backgroundUnsplash(projectMessage.getBackgroundUnsplash())
                    .build();

            user.addProject(project);
            userRepository.save(user);

            log.info("User-{} created project-{}",userId, projectMessage.getId());
            return;
        }
        log.error("User-{} not found to create project-{}",userId, projectMessage.getId());
    }

    @Override
    public void updateWorkSpace(WorkSpaceMessage workSpaceMessage) {
        List<UserRelation> userRelations = workSpaceMessage.getUsers();
        userRelations.forEach(user->{
            updateWorkSpaceUser(user.getUserId(), workSpaceMessage);
        });
    }

    @Override
    public void updateProject(ProjectMessage projectMessage) {
        List<UserRelation> userRelations = projectMessage.getUsers();
        userRelations.forEach(user->{
            updateProjectUser(user.getUserId(), projectMessage);
        });
    }

    @Override
    public void joinWorkSpace(UserJoinWorkSpaceMessage userJoinWorkSpaceMessage) {
        Optional<User> userOptional = userRepository.findById(userJoinWorkSpaceMessage.getUserId());
        if(userOptional.isPresent()){
            User user = userOptional.get();

            //check user is joined?
            boolean isJoined = false;
            for(User.WorkSpace workSpace: user.getWorkspaces()){
                if(StringUtils.equals(workSpace.getId(), userJoinWorkSpaceMessage.getId())){
                    isJoined = true;
                    break;
                }
            }
            if( isJoined ){
                log.error("User-{} already joined workspace-{}",userJoinWorkSpaceMessage.getUserId(), userJoinWorkSpaceMessage.getId());
                return;
            }

            User.WorkSpace workSpace = User.WorkSpace.builder()
                    .id(userJoinWorkSpaceMessage.getId())
                    .name(userJoinWorkSpaceMessage.getName())
                    .build();

            Set<User.WorkSpace> workSpaces = user.getWorkspaces();
            workSpaces.add(workSpace);
            user.setWorkspaces(workSpaces);
            userRepository.save(user);

            log.info("User-{} joined workspace-{}",userJoinWorkSpaceMessage.getUserId(), userJoinWorkSpaceMessage.getId());
        } else {
            log.error("User-{} not found to join workspace-{}",userJoinWorkSpaceMessage.getUserId(), userJoinWorkSpaceMessage.getId());
        }

    }

    @Override
    public void joinProject(UserJoinProjectMessage userJoinProjectMessage) {
        Optional<User> userOptional = userRepository.findById(userJoinProjectMessage.getUserId());
        if(userOptional.isPresent()){
            User user = userOptional.get();

            User.Project project = User.Project.builder()
                    .id(userJoinProjectMessage.getId())
                    .name(userJoinProjectMessage.getName())
                    .build();

            user.addProject(project);
            userRepository.save(user);

            log.info("User-{} joined project-{}",userJoinProjectMessage.getUserId(), userJoinProjectMessage.getId());
        } else {
            log.error("User-{} not found to join project-{}",userJoinProjectMessage.getUserId(), userJoinProjectMessage.getId());
        }
    }

    private void updateWorkSpaceUser(String userId, WorkSpaceMessage workSpaceMessage){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.getWorkspaces().forEach(workSpace -> {
                if(StringUtils.equals(workSpace.getId(), workSpaceMessage.getId())){
                    workSpace.setName(workSpaceMessage.getName());
                    workSpace.setBackgroundUnsplash(workSpaceMessage.getBackgroundUnsplash());
                    log.info("User-{} updated workspace-{}",userId, workSpaceMessage.getId());
                }
            });
            userRepository.save(user);
        }
        log.error("User-{} not found to update workspace-{}",userId, workSpaceMessage.getId());
    }

    private void updateProjectUser(String userId, ProjectMessage projectMessage){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.getProjects().forEach(project -> {
                if(StringUtils.equals(project.getId(), projectMessage.getId())){
                    project.setName(projectMessage.getName());
                    project.setBackgroundUnsplash(projectMessage.getBackgroundUnsplash());
                    project.setBackgroundColor(projectMessage.getBackgroundColor());
                    log.info("User-{} updated project-{}",userId, projectMessage.getId());
                }
            });
            userRepository.save(user);
        }
        log.error("User-{} not found to update project-{}",userId, projectMessage.getId());
    }

    private UserGeneralResponse getUserGeneralResponse(User user){
        UserGeneralResponse userGeneralResponse = modelMapper.map(user, UserGeneralResponse.class);
        userGeneralResponse.setPersonalWorkSpace(user.getPersonalWorkSpace().getId());
        return userGeneralResponse;
    }
}
