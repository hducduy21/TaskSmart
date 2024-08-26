package com.tasksmart.resource.services.impls;

import com.tasksmart.resource.dtos.responses.AvatarResponse;
import com.tasksmart.resource.models.Avatar;
import com.tasksmart.resource.repositories.AvatarRepository;
import com.tasksmart.resource.services.AvatarService;
import com.tasksmart.sharedLibrary.configs.AppConstant;
import com.tasksmart.sharedLibrary.exceptions.InternalServerError;
import com.tasksmart.sharedLibrary.exceptions.ResourceNotFound;
import com.tasksmart.sharedLibrary.models.enums.EGender;
import com.tasksmart.sharedLibrary.services.AwsS3Service;
import com.tasksmart.sharedLibrary.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AvatarServiceImpl implements AvatarService {
    private final AvatarRepository avatarRepository;
    private final AwsS3Service awsS3Service;
    private final FileUtil fileUtil;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<AvatarResponse> getAllAvatars() {
        return avatarRepository.findAll().stream().map(this::getAvatarResponse).toList();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public AvatarResponse createAvatarDefault(MultipartFile img, EGender gender) {
        try{
            fileUtil.requireMaxSize(img, 5);
            fileUtil.requireImage(img);

            String pathId  = UUID.randomUUID() + "." + fileUtil.getFileExtension(img);
            awsS3Service.uploadFile(pathId, AppConstant.IMG_AVATAR_FOLDER, img);

            Avatar avatar = Avatar.builder()
                    .gender(gender)
                    .imageId(pathId)
                    .build();

            avatarRepository.save(avatar);
            return getAvatarResponse(avatar);
        } catch (Exception e) {
            log.error("Error uploading image to S3: {}", e.getMessage());
            throw new InternalServerError("Error uploading image to S3");
        }
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAvatar(String id) {
        Avatar avatar = avatarRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Avatar not found"));
        awsS3Service.deleteFile(avatar.getImageId(), AppConstant.IMG_AVATAR_FOLDER);
        avatarRepository.delete(avatar);
    }

    @Override
    public byte[] getAvatarImage(String imgId) {
        try {
            return awsS3Service.getByte(imgId, AppConstant.IMG_AVATAR_FOLDER);
        }catch (Exception e){
            log.error("Error getting image from S3: {}", e.getMessage());
            throw new ResourceNotFound("Error getting image from S3");
        }
    }

    @Override
    public String randomAvatarByGender(EGender gender) {
        List<Avatar> avatar = avatarRepository.findAllByGender(gender);
        if(CollectionUtils.isEmpty(avatar)){
            return "";
        }
        Avatar randomAvatar = avatar.get((int) (Math.random() * avatar.size()));
        AvatarResponse avatarResponse = getAvatarResponse(randomAvatar);

        return avatarResponse.getImagePath();
    }

    private AvatarResponse getAvatarResponse(Avatar avatar) {
        return AvatarResponse.builder()
                .id(avatar.getId())
                .imagePath("avatars/" + avatar.getImageId())
                .build();
    }
}
