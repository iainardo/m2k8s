package com.manning.liveproject.m2k8s.integration;

import com.manning.liveproject.m2k8s.rest.api.Profile;
import com.manning.liveproject.m2k8s.util.ResourceReaderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@Disabled // TODO Made a start on a basic integration test..to finish this later..Disabled for now
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Tag("integration")
public class ProfileIntegrationTests {

    public static final String HTTP_LOCALHOST = "http://localhost:";
    public static final String FWD_SLASH = "/";
    public static final String IMAGE = "image";

    private int port = 8080;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Setup method used to initialise the restTemplate
     */
    @BeforeEach
    public void init() {
        restTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList((request, body, execution) -> {
                    // found that Content-Type already has text/plain value 'text/plain;charset=UTF-8'
                    // hence the need for the removal of the Content_Type key-value
                    request.getHeaders().remove("Content-Type");
                    request.getHeaders()
                            .add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
                    return execution.execute(request, body);
                }));
    }

    @Test
    public void createProfile() {
        String username = "unamerkel";
        String jsonFileContainingPayload = username + ".json";
        final String ueContextDataPayload = ResourceReaderUtil.getFileContent(jsonFileContainingPayload);
        String contextResourceUrl = baseUrl(Arrays.asList());

        ResponseEntity<Profile> createdProfile =
                this.restTemplate
                        .postForEntity(contextResourceUrl, ueContextDataPayload, Profile.class);
        assertThat(createdProfile).isNotNull();
        assertThat(createdProfile.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);

        contextResourceUrl = baseUrl(Arrays.asList(username));

        ResponseEntity<Profile> retrivedProfile =
                this.restTemplate.getForEntity(
                        contextResourceUrl, Profile.class);
        assertThat(createdProfile).isNotNull();
        assertThat(retrivedProfile).isNotNull();
        assertThat(retrivedProfile.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(retrivedProfile).isEqualTo(createdProfile);
    }


    @Test
    public void testGetProfileNotFound() {
        String username = "russcolombo";
        String contextResourceUrl = baseUrl(Arrays.asList(username));
        ResponseEntity<Profile> retrievedProfile =
                this.restTemplate
                        .getForEntity(contextResourceUrl, Profile.class);
        assertThat(retrievedProfile).isNotNull();
        assertThat(retrievedProfile.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testUploadUserProfileAvatar() {
        String username = "unamerkel";
        File ghost = ResourceReaderUtil.getFile("ghost.jpg");
        String contextResourceUrl = baseUrl(Arrays.asList(username, IMAGE));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("file", ghost);
        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);
        ResponseEntity<Profile> retrievedProfile =
                this.restTemplate
                        .postForEntity(contextResourceUrl, requestEntity, Profile.class);
        assertThat(retrievedProfile).isNotNull();
        assertThat(retrievedProfile.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(retrievedProfile.getBody().getImageFilename()).isEqualTo(ghost.getName());
        assertThat(retrievedProfile.getBody().getImageFileContentType()).isNotEmpty();
    }


    private String baseUrl(List<String> options) {
        String baseUrl = HTTP_LOCALHOST + port + FWD_SLASH;
        StringBuilder baseResourceUrl =
                new StringBuilder(baseUrl)
                        .append("profile");
        for (String opt : options) {
            baseResourceUrl.append(FWD_SLASH)
                    .append(opt);
        }
        return baseResourceUrl.toString();
    }

}
