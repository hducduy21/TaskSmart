package com.tasksmart.user.services.impls;

import com.tasksmart.sharedLibrary.exceptions.BadRequest;
import com.tasksmart.sharedLibrary.exceptions.InternalServerError;
import com.tasksmart.sharedLibrary.repositories.httpClients.NotificationClient;
import com.tasksmart.sharedLibrary.services.AwsS3Service;
import com.tasksmart.user.dtos.request.UserInformationUpdateRequest;
import com.tasksmart.user.dtos.request.UserRegistrationRequest;
import com.tasksmart.user.dtos.response.UserResponse;
import com.tasksmart.user.models.User;
import com.tasksmart.user.repositories.UserRepository;
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
import org.springframework.web.multipart.MultipartFile;

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
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final WorkSpaceClient workSpaceClient;
    private final NotificationClient notificationClient;
    private final KafkaTemplate<String,Object> kafkaTemplate;
    private final AuthenticationUtils authenticationUtils;
    private final AwsS3Service awsS3Service;

    /** {@inheritDoc} */
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<UserResponse> getAllUser() {
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();

        log.info("Get all user: {}", auth.getName());
        auth.getAuthorities().forEach(authority -> {
            log.info("Authority: {}", authority.getAuthority());
        });

        List<User> users = userRepository.findAll();
        return users.stream().map(this::getUserResponse).toList();
    }

    /** {@inheritDoc} */
    @Override
    public UserResponse getUserByIdOrUsername(String idOrUsername) {
        log.info("Get user by id or username: {}", idOrUsername);
        Optional<User> user = userRepository.findById(idOrUsername);

        if(user.isEmpty()){
            user = userRepository.findByUsername(idOrUsername);
        }

        if(user.isEmpty()){
            throw new ResourceNotFound("User not found!");
        }
        return this.getUserResponse(user.get());
    }

    @Override
    public UserResponse getProfile() {
        String userId = authenticationUtils.getUserIdAuthenticated();

        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new ResourceNotFound("User not found!");
        }
        return this.getUserResponse(userOptional.get());
    }

    /** {@inheritDoc} */
    @Override
    public UserResponse createUserById(UserRegistrationRequest userRegistrationRequest) {
        if(!userRegistrationRequest.getPassword().equals(userRegistrationRequest.getConfirmPassword())){
            throw new BadRequest("Password and confirm password do not match!");
        }

        boolean uniqueEmail = !userRepository.existsByEmail(userRegistrationRequest.getEmail());
        if( !uniqueEmail ){
            throw new ResourceConflict("Email already exists!");
        }

        boolean uniqueUsername = !userRepository.existsByUsername(userRegistrationRequest.getUsername());
        if( !uniqueUsername ){
            throw new ResourceConflict("Username already exists!");
        }

        boolean validEmailCode = notificationClient.verifyEmail(userRegistrationRequest.getEmail(), userRegistrationRequest.getVerifyCode());
        System.out.println("validEmailCode: " + validEmailCode);
        if(!validEmailCode){
            throw new BadRequest(1031,"The email verification code is incorrect or has expired!");
        }

        HashSet<String> roles = new HashSet<>();
        roles.add(AppConstant.Role_User);
        User user = modelMapper.map(userRegistrationRequest, User.class);
        user.setPassword(passwordEncoder.encode(userRegistrationRequest.getPassword()));
        user.setRole(roles);
        user.setEnabled(true);
        user.setLocked(false);
        userRepository.save(user);
        try {
            System.out.println("User: " + user.getId());
            WorkSpaceGeneralResponse workSpaceGeneralResponse = workSpaceClient.createPersonalWorkSpace(user.getId(), user.getName(), user.getUsername());
            User.WorkSpace personalWorkSpace = User.WorkSpace.builder()
                    .id(workSpaceGeneralResponse.getId())
                    .name(workSpaceGeneralResponse.getName())
                    .build();
            user.setPersonalWorkSpace(personalWorkSpace);
        }catch (Exception e){
            userRepository.delete(user);
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

        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new ResourceNotFound("User not found!");
        }

        User user = userOptional.get();
        user.setName(userInformationUpdateRequest.getName());
        user.setEmail(userInformationUpdateRequest.getEmail());
        user.setUsername(userInformationUpdateRequest.getUsername());
        user.setProfileBackground(userInformationUpdateRequest.getProfileBackground());

        userRepository.save(user);

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
        boolean exists = userRepository.existsById(id);
        if(!exists){
            throw new ResourceNotFound("User not found!");
        }
        userRepository.deleteById(id);
    }

    @Override
    public byte[] getProfileImage() {
        String userId = authenticationUtils.getUserIdAuthenticated();
        try {
            return awsS3Service.getByte(userId);
        }catch (Exception e){
            log.error("Error: get profile image {}", e.getMessage());
            throw new InternalServerError("Error getting profile image! Please try later.");
        }
    }

    @Override
    public void uploadProfileImage(MultipartFile file) {
        String userId = authenticationUtils.getUserIdAuthenticated();
        try {
            awsS3Service.uploadFile(userId, file);
        }catch (Exception e){
            log.error("Error: upload profile image {}", e.getMessage());
            throw new InternalServerError("Error uploading profile image! Please try later.");
        }

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