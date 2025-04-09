package com.bank.profile.Mappers;

import com.bank.profile.DTO.PassportDto;
import com.bank.profile.Entities.Passport;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PassportMapper {

    PassportDto toDTO(Passport passport);

    Passport toEntity(PassportDto passportDto);
}
