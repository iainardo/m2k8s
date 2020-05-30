package com.manning.liveproject.m2k8s.mapper;

import com.manning.liveproject.m2k8s.data.model.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.NullValueCheckStrategy;

/**
 * Mapping api to data model class using MapStruct (see mapstruct.org)
 */
public class Mappers {

    @MapperConfig(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    public interface DefaultMappingConfig {
    }

    @Mapper(componentModel = "spring", config = DefaultMappingConfig.class)
    public interface ProfileMapper {
        Profile toDataModel(com.manning.liveproject.m2k8s.rest.api.Profile profile);
        com.manning.liveproject.m2k8s.rest.api.Profile toApiModel(Profile var1);
    }


}
