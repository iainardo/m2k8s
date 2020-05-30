package com.manning.liveproject.m2k8s.service;

import com.manning.liveproject.m2k8s.data.ProfileRepository;
import com.manning.liveproject.m2k8s.exception.ProfileAvatarUploadException;
import com.manning.liveproject.m2k8s.exception.ProfileNotFoundException;
import com.manning.liveproject.m2k8s.exception.UsernameProfileIntegrityViolationException;
import com.manning.liveproject.m2k8s.mapper.Mappers;
import com.manning.liveproject.m2k8s.rest.api.Profile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;

/**
 * Service class to handle ProfileService business rules
 */
@Service
@Transactional
public class DefaultProfileService implements ProfileService {
    private static final Log LOGGER = LogFactory.getLog(DefaultProfileService.class);

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private Mappers.ProfileMapper profileMapper;

    @Override
    public Profile get(String username) {
        com.manning.liveproject.m2k8s.data.model.Profile profile = profileRepository.findByUsername(username);
        if (profile == null) {
            throw new ProfileNotFoundException(username);
        }
        return profileMapper.toApiModel(profile);
    }

    @Override
    public Profile get(Long id) {
        com.manning.liveproject.m2k8s.data.model.Profile profile = profileRepository.getOne(id);
        if (profile == null) {
            throw new ProfileNotFoundException(id);
        }
        return profileMapper.toApiModel(profile);
    }

    @Override
    public Profile add(Profile profile) {
        Profile result = null;
        try {
            String username = profile.getUsername();
            com.manning.liveproject.m2k8s.data.model.Profile p = profileRepository.findByUsername
                    (username);
            if (p != null) { // profile already exists so update it
                result = update(username, profile);
            } else { // add new profile
                mapProfileToDateModelAndSave(profile);
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }

        return result;
    }

    private Profile mapProfileToDateModelAndSave(Profile profile) {
        com.manning.liveproject.m2k8s.data.model.Profile profileToPersist =
                profileMapper.toDataModel(profile);
        com.manning.liveproject.m2k8s.data.model.Profile persistedProfile =
                profileRepository.save(profileToPersist);
        return profileMapper.toApiModel(persistedProfile);
    }

    @Override
    public boolean delete(Long id) {
        Boolean result = true;
        try {
            Profile profile = get(id);
            profileRepository.delete(profileMapper.toDataModel(profile));

        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    @Override
    public Profile update(String username, Profile profile) {
        profile = get(username);
        //Perform check to ensure specified username is the username of the specified profile
        if (!profile.getUsername().equals(username)) {
            throw new UsernameProfileIntegrityViolationException(username, profile);
        }
        com.manning.liveproject.m2k8s.data.model.Profile updatedProfile =
                profileRepository.save(profileMapper.toDataModel(profile));
        return profileMapper.toApiModel(updatedProfile);
    }

    @Override
    public Boolean storeProfileImage(String username, MultipartFile avatar) {
        Boolean result = false;
        Profile profile = get(username);
        try {
            String filePath = System.getProperty("user.dir") + avatar.getOriginalFilename();
            profile.setImageFilename(avatar.getOriginalFilename());
            profile.setImageFileContentType(avatar.getContentType());
            this.update(username, profile);
            avatar.transferTo(new File(filePath));
            result = true;
        } catch (IOException e) {
            throw new ProfileAvatarUploadException(username);
        }
        return result;
    }
}
