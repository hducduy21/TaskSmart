package com.example.user.controllers.internals;

import com.example.user.dtos.response.UserGeneralResponse;
import com.example.user.dtos.response.UserResponse;
import com.example.user.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/${url_base}/internal/${url_user}")
@Slf4j
public class UserInternalController {
    private final UserService userService;

    @GetMapping("list")
    public List<UserGeneralResponse> getUsersGeneralByListId(@RequestBody List<String> userIds){
        return userService.getUsersGeneralByListId(userIds);
    }

    @GetMapping("{userId}")
    public UserGeneralResponse getUserGeneralById(@PathVariable String userId){
        return userService.getUserGeneralById(userId);
    }
}
