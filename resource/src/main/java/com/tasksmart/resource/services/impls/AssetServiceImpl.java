package com.tasksmart.resource.services.impls;

import com.tasksmart.resource.services.AssetService;
import com.tasksmart.sharedLibrary.configs.AppConstant;
import com.tasksmart.sharedLibrary.exceptions.ResourceNotFound;
import com.tasksmart.sharedLibrary.services.AwsS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssetServiceImpl implements AssetService {
    private final AwsS3Service awsS3Service;

    @Override
    public byte[] getImage(String imgId) {
        try {
            return awsS3Service.getByte(imgId);
        }catch (Exception e){
            log.error("Error getting image from S3: {}", e.getMessage());
            throw new ResourceNotFound("Error getting image from S3");
        }
    }

    @Override
    public byte[] getUserProfileImage(String imgId) {
        try {
            return awsS3Service.getByte(imgId, AppConstant.IMG_USER_FOLDER+"/" + imgId);
        }catch (Exception e){
            log.error("Error getting image from S3: {}", e.getMessage());
            throw new ResourceNotFound("Error getting image from S3");
        }
    }
}
