package com.example.user.services.impls;

import com.example.user.dtos.request.UserRegistrationRequest;
import com.example.user.models.User;
import com.example.user.models.enums.ERole;
import com.example.user.repositories.UserRepositories;
import com.example.user.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    /** {@inheritDoc} */
    @Override
    public List<User> getAllUser() {
        List<User> users = userRepositories.findAll();
        return users;
    }

    /** {@inheritDoc} */
    @Override
    public User getUserById(String id) {
        Optional<User> user = userRepositories.findById(id);
        return user.orElse(null);
    }

    /** {@inheritDoc} */
    @Override
    public User createUserById(UserRegistrationRequest userRegistrationRequest) {
        User user = modelMapper.map(userRegistrationRequest, User.class);
        user.setPassword(passwordEncoder.encode(userRegistrationRequest.getPassword()));
        user.setRole(ERole.User);
        user.setEnabled(true);
        user.setLocked(false);
        userRepositories.save(user);
        return user;
    }

    /** {@inheritDoc} */
    @Override
    public User updateUserById() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public User changeUserPassword() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public void deleteById(String id) {
        userRepositories.deleteById(id);
    }
}
