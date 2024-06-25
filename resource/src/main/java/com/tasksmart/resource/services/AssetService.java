package com.tasksmart.resource.services;

public interface AssetService {
    byte[] getImage(String imgId);

    byte[] getUserProfileImage(String imgId);
}
