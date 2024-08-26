package com.tasksmart.resource.controllers;

import com.tasksmart.resource.services.AssetService;
import com.tasksmart.resource.services.AvatarService;
import com.tasksmart.resource.services.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * This class is the controller for the image assets.
 *
 * @author Duy Hoang
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/${url_base}/image")
@Slf4j
public class AssetController {
    /**
     * The service for the image assets.
     */
    private final AssetService assetService;

    /**
     * The service for the templates.
     */
    private final AvatarService avatarService;

    /**
     * Get an image by its id.
     *
     * @param imgId The id of the image
     * @return The image
     */
    @GetMapping(value = "{imgId}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public byte[] getImage(@PathVariable String imgId){
        return assetService.getImage(imgId);
    }

    /**
     * Get a user profile image by its id.
     *
     * @param imgId The id of the image
     * @return The image
     */
    @GetMapping(value = "user/{imgId}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public byte[] getUserProfileImage(@PathVariable String imgId){
        return assetService.getUserProfileImage(imgId);
    }

    /**
     * Get an avatar image by its id.
     *
     * @param imgId The id of the image
     * @return The image
     */
    @GetMapping(value = "avatars/{imgId}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public byte[] getImageAvatar(@PathVariable String imgId){
        return avatarService.getAvatarImage(imgId);
    }
}
