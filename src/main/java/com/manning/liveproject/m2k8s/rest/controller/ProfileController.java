package com.manning.liveproject.m2k8s.rest.controller;

import com.manning.liveproject.m2k8s.exception.ProfileNotFoundException;
import com.manning.liveproject.m2k8s.rest.api.Profile;
import com.manning.liveproject.m2k8s.service.DefaultProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RequestMapping(value = "/profile",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
public class ProfileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);


    @Autowired
    private DefaultProfileService defaultProfileService;

    @RequestMapping(value = "/{username}", method = GET)
    public ResponseEntity<Profile> get(@PathVariable final String username) {
        return ResponseEntity.ok().body(defaultProfileService.get(username));
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Boolean> delete(@PathVariable final Long id) {
        ResponseEntity<Boolean> response = null;
        Boolean successfulDelete = false;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            successfulDelete = defaultProfileService.delete(id);
        } catch (ProfileNotFoundException e) {
            response = ResponseEntity.notFound().headers(headers).build();
        } finally {
            if (Boolean.TRUE.equals(successfulDelete)) {
                response = ResponseEntity
                        .ok().headers(headers).build();
            } else {
                response = ResponseEntity.badRequest().headers(headers).build();
            }
        }
        return response;
    }

    @RequestMapping("/{username}/image")
    @PostMapping
    public ResponseEntity<Boolean> upload(@PathVariable String username,
                                          @RequestParam("file") MultipartFile avatar) {
        ResponseEntity<Boolean> response;
        Boolean result = defaultProfileService.storeProfileImage(username, avatar);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (Boolean.TRUE.equals(result)) {
            response = ResponseEntity
                    .ok().headers(headers).build();
        } else {
            response = ResponseEntity.badRequest().headers(headers).build();
        }
        return response;
    }

    @PostMapping
    public ResponseEntity<Profile> add(@RequestBody final Profile profile) {
        return toResponseEntity(defaultProfileService.add(profile));
    }

    @RequestMapping(value = "/{username}", method = PUT)
    public ResponseEntity<Profile> update(@PathVariable String username, @RequestBody final Profile profile) {
        return toResponseEntity(defaultProfileService.update(username, profile));
    }


    private ResponseEntity<Profile> toResponseEntity(Profile profile) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .build()
                .toUri();
        return ResponseEntity.created(location).body(profile);

    }

}
