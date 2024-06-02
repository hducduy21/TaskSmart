package com.tasksmart.user.services.impls;

import com.tasksmart.user.dtos.request.UserInformationUpdateRequest;
import com.tasksmart.user.dtos.request.UserRegistrationRequest;
import com.tasksmart.user.dtos.response.UserResponse;
import com.tasksmart.user.models.User;
import com.tasksmart.user.repositories.UserRepositories;
import com.tasksmart.user.services.UserService;
import com.tasksmart.sharedLibrary.configs.AppConstant;
import com.tasksmart.sharedLibrary.configs.KafkaMessageConverter;
import com.tasksmart.sharedLibrary.dtos.messages.UserMessage;
import com.tasksmart.sharedLibrary.dtos.responses.WorkSpaceGeneralResponse;
import com.tasksmart.sharedLibrary.exceptions.ResourceConflict;
import com.tasksmart.sharedLibrary.exceptions.ResourceNotFound;
import com.tasksmart.sharedLibrary.repositories.httpClients.WorkSpaceClient;
import com.tasksmart.sharedLibrary.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the UserService interface.
 * This class provides methods to perform various operations related to users.
 *
 * @author Duy Hoang
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepositories userRepositories;
    private final ModelMapper modelMapper;
    private final WorkSpaceClient workSpaceClient;
    private final KafkaTemplate<String,Object> kafkaTemplate;
    private final AuthenticationUtils authenticationUtils;

    /** {@inheritDoc} */
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<UserResponse> getAllUser() {
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();

        log.info("Get all user: {}", auth.getName());
        auth.getAuthorities().forEach(authority -> {
            log.info("Authority: {}", authority.getAuthority());
        });

        List<User> users = userRepositories.findAll();
        return users.stream().map(this::getUserResponse).toList();
    }

    /** {@inheritDoc} */
    @Override
    public UserResponse getUserByIdOrUsername(String idOrUsername) {
        log.info("Get user by id or username: {}", idOrUsername);
        Optional<User> user = userRepositories.findById(idOrUsername);

        if(user.isEmpty()){
            user = userRepositories.findByUsername(idOrUsername);
        }

        if(user.isEmpty()){
            throw new ResourceNotFound("User not found!");
        }
        return this.getUserResponse(user.get());
    }

    @Override
    public UserResponse getProfile() {
        String userId = authenticationUtils.getUserIdAuthenticated();

        Optional<User> userOptional = userRepositories.findById(userId);
        if(userOptional.isEmpty()){
            throw new ResourceNotFound("User not found!");
        }
        return this.getUserResponse(userOptional.get());
    }

    /** {@inheritDoc} */
    @Override
    public UserResponse createUserById(UserRegistrationRequest userRegistrationRequest) {
        boolean uniqueEmail = !userRepositories.existsByEmail(userRegistrationRequest.getEmail());
        if( !uniqueEmail ){
            throw new ResourceConflict("Email already exists!");
        }

        boolean uniqueUsername = !userRepositories.existsByUsername(userRegistrationRequest.getUsername());
        if( !uniqueUsername ){
            throw new ResourceConflict("Username already exists!");
        }

        HashSet<String> roles = new HashSet<>();
        roles.add(AppConstant.Role_User);
        User user = modelMapper.map(userRegistrationRequest, User.class);
        try {
            user.setPassword(passwordEncoder.encode(userRegistrationRequest.getPassword()));
            user.setRole(roles);
            user.setEnabled(true);
            user.setLocked(false);

            WorkSpaceGeneralResponse workSpaceGeneralResponse = workSpaceClient.createPersonalWorkSpace(user.getId(), user.getName(), user.getUsername());
            User.WorkSpace personalWorkSpace = User.WorkSpace.builder()
                    .id(workSpaceGeneralResponse.getId())
                    .name(workSpaceGeneralResponse.getName())
                    .build();
            user.setPersonalWorkSpace(personalWorkSpace);

            userRepositories.save(user);
        }catch (Exception e){
            log.error("Error: create user {}", e.getMessage());
            throw new ResourceConflict("Error creating user! Please try later.");
        }

        kafkaTemplate.setMessageConverter(new KafkaMessageConverter());
        kafkaTemplate.send("user-registration", modelMapper.map(user, UserMessage.class));

        return this.getUserResponse(user);
    }

    /** {@inheritDoc} */
    @Override
    public UserResponse updateUser(UserInformationUpdateRequest userInformationUpdateRequest) {
        String userId = authenticationUtils.getUserIdAuthenticated();

        Optional<User> userOptional = userRepositories.findById(userId);
        if(userOptional.isEmpty()){
            throw new ResourceNotFound("User not found!");
        }

        User user = userOptional.get();
        user.setName(userInformationUpdateRequest.getName());
        user.setEmail(userInformationUpdateRequest.getEmail());
        user.setUsername(userInformationUpdateRequest.getUsername());
        user.setProfileBackground(userInformationUpdateRequest.getProfileBackground());

        userRepositories.save(user);

        kafkaTemplate.setMessageConverter(new KafkaMessageConverter());
        kafkaTemplate.send("user-updation", modelMapper.map(user, UserMessage.class));

        return this.getUserResponse(user);
    }

    /** {@inheritDoc} */
    @Override
    public UserResponse changeUserPassword() {
        return null;
    }

    /** {@inheritDoc} */
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void deleteById(String id) {
        boolean exists = userRepositories.existsById(id);
        if(!exists){
            throw new ResourceNotFound("User not found!");
        }
        userRepositories.deleteById(id);
    }

    /**
     * This method convert user to user response dto.
     *
     * @param user object user want to convert
     * @return user response converted
     */
    public UserResponse getUserResponse(User user){
        return modelMapper.map(user,UserResponse.class);
    }

}
