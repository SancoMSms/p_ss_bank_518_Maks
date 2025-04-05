package com.bank.profile.Mappers;

import com.bank.profile.DTO.PassportDto;
import com.bank.profile.Entities.Passport;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PassportMapper {
    PassportMapper INSTANCE = Mappers.getMapper(PassportMapper.class);

    PassportDto toDTO(Passport passport);

    Passport toEntity(PassportDto passportDto);
}
