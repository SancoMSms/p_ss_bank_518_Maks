package com.bank.profile.Mappers;

import com.bank.profile.DTO.RegistrationDto;
import com.bank.profile.Entities.Registration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RegistrationMappers {
    RegistrationMappers INSTANCE = Mappers.getMapper(RegistrationMappers.class);

    RegistrationDto toDTO(Registration registration);

    Registration toEntity(RegistrationDto registrationDto);
}
