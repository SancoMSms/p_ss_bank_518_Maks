
package com.bank.profile.Mappers;

import com.bank.profile.DTO.ActualRegistrationDto;
import com.bank.profile.Entities.ActualRegistration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActualRegistrationMapper {

    ActualRegistrationDto toDTO(ActualRegistration actualRegistration);

    ActualRegistration toEntity(ActualRegistrationDto actualRegistrationDto);
}
