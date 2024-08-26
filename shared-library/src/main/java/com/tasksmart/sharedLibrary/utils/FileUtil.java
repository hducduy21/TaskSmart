package com.tasksmart.sharedLibrary.utils;

import com.tasksmart.sharedLibrary.exceptions.BadRequest;
import com.tasksmart.sharedLibrary.models.enums.EFileType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtil {

    public String getFileExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            return null;
        }
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex < 0) {
            return null;
        }
        return fileName.substring(dotIndex + 1);
    }

    public EFileType classifyFileType(MultipartFile file) {
        String extension = getFileExtension(file);
        if (extension == null) {
            return EFileType.unknown;
        }
        switch (extension.toLowerCase()) {
            case "jpg":
            case "jpeg":
            case "png":
            case "gif":
            case "bmp":
                return EFileType.image;
            case "pdf":
                return EFileType.pdf;
            default:
                return EFileType.unknown;
        }
    }

    public void requireImage(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new BadRequest("Invalid file type. Please provide an image file.");
        }
    }

    public void requireMaxSize(MultipartFile file, long sizeInMb) {
        if (file.getSize() > sizeInMb * 1024 * 1024) { // 2MB
            throw new IllegalArgumentException("File size must be less than 2MB.");
        }
    }
}
