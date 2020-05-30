package com.manning.liveproject.m2k8s.service;

import com.manning.liveproject.m2k8s.rest.api.Profile;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {
    Profile get(String username);

    Profile get(Long id);

    Profile add(Profile profile);

    boolean delete(Long id);

    Profile update(String username, Profile profile);

    Boolean storeProfileImage(String username, MultipartFile avatar);
}
