package com.example.user.controllers;

import com.example.user.configs.AppConstant;
import com.example.user.dtos.request.UserRegistrationRequest;
import com.example.user.models.User;
import com.example.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/${url_base}/${url_user}")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getAllUser(){
        return userService.getAllUser();
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable String id){
        return userService.getUserById(id);
    }

    @PostMapping
    public User createUser(@RequestBody UserRegistrationRequest userRegistrationRequest){
        return userService.createUserById(userRegistrationRequest);
    }

    @PatchMapping
    public User changePassword(){
        //wait to authentication function.
        return null;
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable String id){
        userService.deleteById(id);
    }
}
