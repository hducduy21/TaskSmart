package com.tasksmart.resource.controllers;

import com.tasksmart.resource.dtos.responses.AvatarResponse;
import com.tasksmart.resource.services.AvatarService;
import com.tasksmart.sharedLibrary.models.enums.EGender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * This class is the controller for the avatars.
 * It handles the requests related to the avatars.
 *
 * @author Duy Hoang
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/${url_base}/${url_avatar}")
@Slf4j
public class AvatarController {
    private final AvatarService avatarService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AvatarResponse> getAllAvatars(){
        return avatarService.getAllAvatars();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AvatarResponse createAvatar(@RequestPart MultipartFile img, @RequestParam EGender gender){
        return avatarService.createAvatarDefault(img, gender);
    }

    @DeleteMapping("/{avatartId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAvatar(@PathVariable String avatartId){
        avatarService.deleteAvatar(avatartId);
    }

    @GetMapping("/random")
    @ResponseStatus(HttpStatus.OK)
    public String randomAvatar(@RequestParam EGender gender){
        return avatarService.randomAvatarByGender(gender);
    }
}
