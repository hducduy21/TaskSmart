package com.tasksmart.user.services.impls;

import com.tasksmart.sharedLibrary.utils.AuthenticationUtils;
import com.tasksmart.user.dtos.request.UserSignInRequest;
import com.tasksmart.user.dtos.response.AuthResponse;
import com.tasksmart.user.dtos.response.UserGeneralResponse;
import com.tasksmart.user.models.User;
import com.tasksmart.user.repositories.UserRepository;
import com.tasksmart.user.services.AuthService;
import com.tasksmart.sharedLibrary.exceptions.BadRequest;
import com.tasksmart.sharedLibrary.exceptions.ResourceNotFound;
import com.tasksmart.sharedLibrary.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

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
    private final UserRepository userRepository;

    /** Mapper instance for converting data between different representations. */
    private final ModelMapper modelMapper;

    /** Encoder instance for hashing and verifying passwords. */
    private final PasswordEncoder passwordEncoder;

    private final JWTUtil jwtUtil;

    private final AuthenticationUtils authenticationUtils;

    @Override
    public UserGeneralResponse introspect() {
        String userId = authenticationUtils.getUserIdAuthenticated();
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFound("User not found!")
        );
        return modelMapper.map(user, UserGeneralResponse.class);
    }

    /** {@inheritDoc} */
    @Override
    public AuthResponse login(UserSignInRequest userSignInRequest) {
        if(StringUtils.isBlank(userSignInRequest.getEmail()) && StringUtils.isBlank(userSignInRequest.getUsername())){
            throw new BadRequest("Please provide complete login information!");
        }

        User user;

        if(StringUtils.isNotBlank(userSignInRequest.getEmail())){
            user = userRepository.findByEmail(userSignInRequest.getEmail()).orElseThrow(
                    () -> new ResourceNotFound("User not found!")
            );
        } else {
            user = userRepository.findByUsername(userSignInRequest.getUsername()).orElseThrow(
                    () -> new ResourceNotFound("User not found!")
            );
        }

        if(! passwordEncoder.matches(userSignInRequest.getPassword(), user.getPassword())){
            throw new BadRequest("Invalid password!");
        }

        UserGeneralResponse userGeneralResponse = getUserResponse(user);
        return AuthResponse.builder()
                .user(userGeneralResponse)
                .accessToken(jwtUtil.generateToken(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getRole(), 1))
                .refreshToken(jwtUtil.generateToken(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getRole(), 24*5))
                .build();
    }

    @Override
    public AuthResponse refresh(String refresh){
        if (refresh == null || !refresh.startsWith("Bearer"))
            throw new BadRequest("Invalid authorization header.");

        String refreshToken = refresh.substring(7);
        Jwt jwt = jwtUtil.jwtDecoder().decode(refreshToken);
        String UserId = jwt.getClaim("userId");

        User user = userRepository.findById(UserId).orElseThrow(
                () -> new ResourceNotFound("User not found!")
        );

        UserGeneralResponse userGeneralResponse = getUserResponse(user);
        return AuthResponse.builder()
                .user(userGeneralResponse)
                .accessToken(jwtUtil.generateToken(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getRole(), 1))
                .refreshToken(refresh)
                .build();
    }

    private UserGeneralResponse getUserResponse(User user){
        return modelMapper.map(user, UserGeneralResponse.class);
    }
}
