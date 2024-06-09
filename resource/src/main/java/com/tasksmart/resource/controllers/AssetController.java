package com.tasksmart.resource.controllers;

import com.tasksmart.resource.services.AssetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/img")
@Slf4j
public class AssetController {
    private final AssetService assetService;

    @GetMapping(value = "{imgId}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public byte[] getImage(@PathVariable String imgId){
        return assetService.getImage(imgId);
    }
}
