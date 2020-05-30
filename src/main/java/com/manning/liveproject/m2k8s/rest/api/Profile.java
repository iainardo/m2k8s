package com.manning.liveproject.m2k8s.rest.api;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Immutable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
@Immutable
public final class Profile {

    private static final String JSON_PROPERTY_ID = "id";
    private static final String JSON_PROPERTY_USERNAME = "username";
    private static final String JSON_PROPERTY_PASSOWRD = "password";
    private static final String JSON_PROPERTY_FIRSTNAME = "firstName";
    private static final String JSON_PROPERTY_LASTNAME = "lastName";
    private static final String JSON_PROPERTY_EMAIL = "email";
    private static final String JSON_PROPERTY_IMAGE_FILENAME = "imageFilename";
    private static final String JSON_PROPERTY_IMAGE_FILE_CONTENT_TYPE = "imageFileContentType";

    @JsonProperty(JSON_PROPERTY_ID)
    private Long id;

    @JsonProperty(JSON_PROPERTY_USERNAME)
    @NotNull
    @Size(min = 5, max = 16)
    private String username;

    @JsonProperty(JSON_PROPERTY_PASSOWRD)
    @NotNull
    @Size(min = 5, max = 25)
    private String password;

    @JsonProperty(JSON_PROPERTY_FIRSTNAME)
    @NotNull
    @Size(min = 2, max = 30)
    private String firstName;

    @JsonProperty(JSON_PROPERTY_LASTNAME)
    @NotNull
    @Size(min = 2, max = 30)
    private String lastName;

    @JsonProperty(JSON_PROPERTY_EMAIL)
    @NotNull
    private String email;

    @JsonProperty(JSON_PROPERTY_IMAGE_FILENAME)
    private String imageFilename;

    @JsonProperty(JSON_PROPERTY_IMAGE_FILE_CONTENT_TYPE)
    private String imageFileContentType;

    public Profile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageFilename() {
        return imageFilename;
    }

    public void setImageFilename(String imageFilename) {
        this.imageFilename = imageFilename;
    }

    public String getImageFileContentType() {
        return imageFileContentType;
    }

    public void setImageFileContentType(String imageFileContentType) {
        this.imageFileContentType = imageFileContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Profile)) return false;
        Profile profile = (Profile) o;
        return Objects.equals(getId(), profile.getId()) &&
                getUsername().equals(profile.getUsername()) &&
                getPassword().equals(profile.getPassword()) &&
                getFirstName().equals(profile.getFirstName()) &&
                getLastName().equals(profile.getLastName()) &&
                getEmail().equals(profile.getEmail()) &&
                Objects.equals(getImageFilename(), profile.getImageFilename()) &&
                Objects.equals(getImageFileContentType(), profile.getImageFileContentType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getPassword(), getFirstName(), getLastName(), getEmail(), getImageFilename(), getImageFileContentType());
    }
}
