
package com.bank.profile.Mappers;

import com.bank.profile.DTO.ActualRegistrationDto;
import com.bank.profile.Entities.ActualRegistration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ActualRegistrationMapper {
    ActualRegistrationMapper INSTANCE = Mappers.getMapper(ActualRegistrationMapper.class);

    ActualRegistrationDto toDTO(ActualRegistration actualRegistration);

    ActualRegistration toEntity(ActualRegistrationDto actualRegistrationDto);
}
