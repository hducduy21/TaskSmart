package com.tasksmart.resource.services;

import com.tasksmart.resource.dtos.responses.AvatarResponse;
import com.tasksmart.sharedLibrary.models.enums.EGender;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AvatarService {
    AvatarResponse createAvatarDefault(MultipartFile img, EGender gender);
    void deleteAvatar(String id);

    List<AvatarResponse> getAllAvatars();

    byte[] getAvatarImage(String imgId);

    String randomAvatarByGender(EGender gender);
}
