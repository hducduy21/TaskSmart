package com.example.user.services.impls;

import com.example.user.dtos.request.UserInformationUpdateRequest;
import com.example.user.dtos.request.UserRegistrationRequest;
import com.example.user.dtos.response.UserResponse;
import com.example.user.models.User;
import com.example.user.models.enums.ERole;
import com.example.user.repositories.UserRepositories;
import com.example.user.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.tasksmart.sharedLibrary.exceptions.ResourceConflict;
import com.tasksmart.sharedLibrary.exceptions.ResourceNotFound;

import java.util.HashSet;
import java.util.List;

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

    /** {@inheritDoc} */
    @Override
    public List<UserResponse> getAllUser() {
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();

        log.info("Get all user: {}", auth.getName());
        auth.getAuthorities().forEach(authority -> {
            log.info("Authority: {}", authority.getAuthority());
        });

        List<User> users = userRepositories.findAll();
        List<UserResponse> userResponses = users.stream().map(this::getUserResponse).toList();
        return userResponses;
    }

    /** {@inheritDoc} */
    @Override
    public UserResponse getUserById(String id) {
        User user = userRepositories.findById(id).orElseThrow(
                ()->new ResourceNotFound("User not found!")
        );
        return this.getUserResponse(user);
    }

    /** {@inheritDoc} */
    @Override
    public UserResponse createUserById(UserRegistrationRequest userRegistrationRequest) {
        boolean uniqueEmail = userRepositories.existsByEmail(userRegistrationRequest.getEmail());
        if(uniqueEmail){
            throw new ResourceConflict("Email already exits!");
        }

        HashSet<ERole> roles = new HashSet<>();
        roles.add(ERole.USER);

        User user = modelMapper.map(userRegistrationRequest, User.class);
        user.setPassword(passwordEncoder.encode(userRegistrationRequest.getPassword()));
        user.setRole(roles);
        user.setEnabled(true);
        user.setLocked(false);
        userRepositories.save(user);
        return this.getUserResponse(user);
    }

    /** {@inheritDoc} */
    @Override
    public UserResponse updateUser(UserInformationUpdateRequest userInformationUpdateRequest) {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public UserResponse changeUserPassword() {
        return null;
    }

    /** {@inheritDoc} */
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
