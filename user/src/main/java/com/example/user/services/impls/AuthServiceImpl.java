package com.example.user.services.impls;

import com.example.user.dtos.request.UserSignInRequest;
import com.example.user.dtos.response.AuthResponse;
import com.example.user.dtos.response.UserGeneralResponse;
import com.example.user.models.User;
import com.example.user.repositories.UserRepositories;
import com.example.user.services.AuthService;
import com.tasksmart.sharedLibrary.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.tasksmart.sharedLibrary.exceptions.ResourceNotFound;
import com.tasksmart.sharedLibrary.exceptions.BadRequest;

/**
 * Implementation of the AuthService interface.
 * This class provides authentication-related functionalities.
 *
 * @author Duy Hoang
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    /** Repository instance for accessing user data. */
    private final UserRepositories userRepositories;

    /** Mapper instance for converting data between different representations. */
    private final ModelMapper modelMapper;

    /** Encoder instance for hashing and verifying passwords. */
    private final PasswordEncoder passwordEncoder;

    private final JWTUtil jwtUtil;

    /** {@inheritDoc} */
    @Override
    public AuthResponse login(UserSignInRequest userSignInRequest) {
        if(StringUtils.isBlank(userSignInRequest.getEmail()) && StringUtils.isBlank(userSignInRequest.getUsername())){
            throw new BadRequest("Please provide complete login information!");
        }

        User user;

        if(StringUtils.isNotBlank(userSignInRequest.getEmail())){
            user = userRepositories.findByEmail(userSignInRequest.getEmail()).orElseThrow(
                    () -> new ResourceNotFound("User not found!")
            );
        } else {
            user = userRepositories.findByUsername(userSignInRequest.getUsername()).orElseThrow(
                    () -> new ResourceNotFound("User not found!")
            );
        }

        if(! passwordEncoder.matches(userSignInRequest.getPassword(), user.getPassword())){
            throw new BadRequest("Invalid password!");
        }

        UserGeneralResponse userGeneralResponse = modelMapper.map(user, UserGeneralResponse.class);
        return AuthResponse.builder()
                .user(userGeneralResponse)
                .accessToken(jwtUtil.generateToken(user.getId(), user.getName(), user.getEmail(), user.getUsername(), user.getRole(), 600))
                .refreshToken("refresh")
                .build();
    }

    public AuthResponse refresh(String auth){
        return null;
    }

}
