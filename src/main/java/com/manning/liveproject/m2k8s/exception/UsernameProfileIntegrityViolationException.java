package com.manning.liveproject.m2k8s.exception;

import com.manning.liveproject.m2k8s.rest.api.Profile;

public class UsernameProfileIntegrityViolationException extends AbstractProfileException {
    private String username;

    public UsernameProfileIntegrityViolationException(String username, Profile profile) {
        super(profile);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
