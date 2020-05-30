package com.manning.liveproject.m2k8s.rest.controller;

import com.manning.liveproject.m2k8s.exception.ProfileNotFoundException;
import com.manning.liveproject.m2k8s.exception.UsernameProfileIntegrityViolationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackageClasses = ProfileController.class)
public class ProfileControllerAdvice {
    private static final Log LOGGER = LogFactory.getLog(ProfileControllerAdvice.class);

    @ExceptionHandler(ProfileNotFoundException.class)
    protected ResponseEntity handleNotFound(ProfileNotFoundException e) {

        LOGGER.warn(String.format("Profile for user '%s' (or by profile id '%s') does not exist " +
                        "payload ", e.getUsername(), e.getId()));
        return ResponseEntity.notFound().build();
    }


    @ExceptionHandler(UsernameProfileIntegrityViolationException.class)
    protected ResponseEntity handleBadRequest(UsernameProfileIntegrityViolationException e) {
        LOGGER.warn(String.format("Username '%s' in profile does not match username in JSON payload ",
                e.getUsername()));
        return ResponseEntity.badRequest().build();
    }


}
