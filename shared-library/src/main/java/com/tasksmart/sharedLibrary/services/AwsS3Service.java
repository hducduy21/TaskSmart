package com.tasksmart.sharedLibrary.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    public void uploadFile(String key, MultipartFile file) throws IOException {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
    }

    public void uploadFile(String key, String folder, MultipartFile file) throws IOException {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(folder + "/" + key)
                .build();

        PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
    }

    public void deleteFile(String key) {
        s3Client.deleteObject(builder -> builder.bucket(bucketName).key(key));
    }

    public void deleteFile(String key, String folder) {
        s3Client.deleteObject(builder -> builder.bucket(bucketName).key(folder + "/" + key));
    }

    public byte[] getByte(String key) throws IOException {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        ResponseInputStream<GetObjectResponse> response = s3Client.getObject(getObjectRequest);
        return response.readAllBytes();
    }

    public byte[] getByte(String key, String folder) throws IOException {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(folder + "/" + key)
                .build();
        ResponseInputStream<GetObjectResponse> response = s3Client.getObject(getObjectRequest);
        return response.readAllBytes();
    }
}
