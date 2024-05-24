package com.example.user.services.impls;

import com.example.user.dtos.request.UserSignInRequest;
import com.example.user.dtos.response.AuthResponse;
import com.example.user.dtos.response.UserGeneralResponse;
import com.example.user.dtos.response.UserResponse;
import com.example.user.exceptions.BadRequest;
import com.example.user.exceptions.ResourceNotFound;
import com.example.user.models.User;
import com.example.user.repositories.UserRepositories;
import com.example.user.services.AuthService;
import com.example.user.utils.JWTUtil;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

/**
 * Implementation of the AuthService interface.
 * This class provides authentication-related functionalities.
 *
 * @author Duy Hoang
 */
@Service
@RequiredArgsConstructor
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
        User user = userRepositories.findByEmail(userSignInRequest.getEmail()).orElseThrow(
                () -> new ResourceNotFound("User not found!")
        );

        if(! passwordEncoder.matches(userSignInRequest.getPassword(), user.getPassword())){
            throw new BadRequest("Invalid password!");
        }

        UserGeneralResponse userGeneralResponse = modelMapper.map(user, UserGeneralResponse.class);
        return AuthResponse.builder()
                .user(userGeneralResponse)
                .accessToken(jwtUtil.generateToken(user, 600))
                .refreshToken("refresh")
                .build();
    }

    public AuthResponse refresh(String auth){
        return null;
    }

}
