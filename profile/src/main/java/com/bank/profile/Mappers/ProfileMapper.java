package com.bank.profile.Mappers;

import com.bank.profile.DTO.ProfileDto;
import com.bank.profile.Entities.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);

    ProfileDto toDTO(Profile profile);

    Profile toEntity(ProfileDto profileDto);
}
