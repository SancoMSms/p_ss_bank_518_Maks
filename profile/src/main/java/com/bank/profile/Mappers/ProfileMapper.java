package com.bank.profile.Mappers;

import com.bank.profile.DTO.ProfileDto;
import com.bank.profile.Entities.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileDto toDTO(Profile profile);

    Profile toEntity(ProfileDto profileDto);
}
