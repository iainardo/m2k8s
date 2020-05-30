package com.manning.liveproject.m2k8s.service;

import com.manning.liveproject.m2k8s.data.ProfileRepository;
import com.manning.liveproject.m2k8s.exception.ProfileAvatarUploadException;
import com.manning.liveproject.m2k8s.exception.ProfileNotFoundException;
import com.manning.liveproject.m2k8s.exception.UsernameProfileIntegrityViolationException;
import com.manning.liveproject.m2k8s.mapper.Mappers;
import com.manning.liveproject.m2k8s.rest.api.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;

@Service
@Transactional
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private Mappers.ProfileMapper profileMapper;


    public Profile get(String username) {
        com.manning.liveproject.m2k8s.data.model.Profile profile = profileRepository.findByUsername(username);
        if (profile == null) {
            throw new ProfileNotFoundException(username);
        }
        return profileMapper.toApiModel(profile);
    }

    public Profile get(Long id) {
        com.manning.liveproject.m2k8s.data.model.Profile profile = profileRepository.getOne(id);
        if (profile == null) {
            throw new ProfileNotFoundException(id);
        }
        return profileMapper.toApiModel(profile);
    }

    public Profile add(Profile profile) {
        com.manning.liveproject.m2k8s.data.model.Profile persistedProfile =
                profileRepository.save(profileMapper.toDataModel(profile));
        return profileMapper.toApiModel(persistedProfile);
    }

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

    public Profile update(String username, Profile profile) {
        get(username);

        //Perform check to ensure specified username is the username of the specified profile
        if(!profile.getUsername().equals(username)) {
            throw new UsernameProfileIntegrityViolationException(username,profile);
        }

        com.manning.liveproject.m2k8s.data.model.Profile updatedProfile =
                profileRepository.save(profileMapper.toDataModel(profile));
        return profileMapper.toApiModel(updatedProfile);
    }

    public Boolean storeProfileImage(String username, MultipartFile avatar) {
        Boolean result = false;
        Profile profile = get(username);
        try {
            String filePath = System.getProperty("user.dir") + avatar.getOriginalFilename();
            profile.setImageFilename(avatar.getOriginalFilename());
            profile.setImageFileContentType(avatar.getContentType());
            this.update(username,profile);
            avatar.transferTo(new File(filePath));
            result = true;
        }
        catch(IOException e ){
            throw new ProfileAvatarUploadException(username);
        }
        return result;
    }
}
