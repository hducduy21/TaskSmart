package com.tasksmart.resource.controllers;

import com.tasksmart.resource.services.AssetService;
import com.tasksmart.resource.services.AvatarService;
import com.tasksmart.resource.services.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/${url_base}/image")
@Slf4j
public class AssetController {
    private final AssetService assetService;
    private final TemplateService templateService;
    private final AvatarService avatarService;

    @GetMapping(value = "{imgId}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public byte[] getImage(@PathVariable String imgId){
        return assetService.getImage(imgId);
    }

    @GetMapping(value = "user/{imgId}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public byte[] getUserProfileImage(@PathVariable String imgId){
        return assetService.getUserProfileImage(imgId);
    }

    @GetMapping(value = "avatars/{imgId}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public byte[] getImageAvatar(@PathVariable String imgId){
        return avatarService.getAvatarImage(imgId);
    }
}
