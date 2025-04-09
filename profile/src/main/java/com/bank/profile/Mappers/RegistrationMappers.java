package com.bank.profile.Mappers;

import com.bank.profile.DTO.RegistrationDto;
import com.bank.profile.Entities.Registration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegistrationMappers {

    RegistrationDto toDTO(Registration registration);

    Registration toEntity(RegistrationDto registrationDto);
}
