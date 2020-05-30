package com.manning.liveproject.m2k8s.exception;

public class ProfileNotFoundException extends RuntimeException {

    private Long id;
    private String username;

    public ProfileNotFoundException(Long id) {
        this.id = id;
    }

    public ProfileNotFoundException(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
