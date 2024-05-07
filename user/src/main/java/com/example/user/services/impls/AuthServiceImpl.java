package com.example.user.services.impls;

import com.example.user.dtos.request.UserSignInRequest;
import com.example.user.models.User;
import com.example.user.repositories.UserRepositories;
import com.example.user.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    /** {@inheritDoc} */
    @Override
    public User login(UserSignInRequest userSignInRequest) {
        User user = userRepositories.findByEmail(userSignInRequest.getEmail()).orElse(null);
        if(! passwordEncoder.matches(userSignInRequest.getPassword(), user.getPassword())){
            return null;
        }
        return user;
    }
}
