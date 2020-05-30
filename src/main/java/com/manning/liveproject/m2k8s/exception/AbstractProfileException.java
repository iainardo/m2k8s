package com.manning.liveproject.m2k8s.exception;

import com.manning.liveproject.m2k8s.rest.api.Profile;

public abstract class AbstractProfileException extends RuntimeException {

    private final Profile profile;

    public AbstractProfileException(final Profile profile) {
        this.profile = profile;
    }


    public Profile getProfile() {
        return profile;
    }
}
