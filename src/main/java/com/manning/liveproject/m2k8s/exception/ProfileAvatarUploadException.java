package com.manning.liveproject.m2k8s.exception;

public class ProfileAvatarUploadException extends RuntimeException {
    private String username;

    public ProfileAvatarUploadException(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
