package com.manning.liveproject.m2k8s.mapper;

import com.manning.liveproject.m2k8s.data.model.Profile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MappersTest {


    private static final Mappers.ProfileMapper MAPPER =
            org.mapstruct.factory.Mappers.getMapper(Mappers.ProfileMapper.class);


    @Test
    void testApiToData() {
        Profile profile = new Profile();
        profile.setUsername("unamerkel");
        profile.setFirstName("Una");
        profile.setLastName("Merkel");
        profile.setPassword("changeme");
        profile.setEmail("unamerkel@example.com");

        com.manning.liveproject.m2k8s.rest.api.Profile persistedProfile = MAPPER.toApiModel(profile);
        assertThat(persistedProfile).isNotNull();
        assertThat(persistedProfile.getUsername()).isEqualTo(profile.getUsername());
        assertThat(persistedProfile.getPassword()).isEqualTo(profile.getPassword());
        assertThat(persistedProfile.getFirstName()).isEqualTo(profile.getFirstName());
        assertThat(persistedProfile.getLastName()).isEqualTo(profile.getLastName());
        assertThat(persistedProfile.getEmail()).isEqualTo(profile.getEmail());
    }


    @Test
    void testDataToAPI() {
        com.manning.liveproject.m2k8s.rest.api.Profile dataProfile  =
                new com.manning.liveproject.m2k8s.rest.api.Profile();
        dataProfile.setUsername("unamerkel");
        dataProfile.setFirstName("Una");
        dataProfile.setLastName("Merkel");
        dataProfile.setPassword("changeme");
        dataProfile.setEmail("unamerkel@example.com");

        Profile apiProfile = MAPPER.toDataModel(dataProfile);
        assertThat(apiProfile).isNotNull();
        assertThat(apiProfile.getUsername()).isEqualTo(dataProfile.getUsername());
        assertThat(apiProfile.getPassword()).isEqualTo(dataProfile.getPassword());
        assertThat(apiProfile.getFirstName()).isEqualTo(dataProfile.getFirstName());
        assertThat(apiProfile.getLastName()).isEqualTo(dataProfile.getLastName());
        assertThat(apiProfile.getEmail()).isEqualTo(dataProfile.getEmail());
    }
}
