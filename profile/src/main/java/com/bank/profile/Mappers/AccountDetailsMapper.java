package com.bank.profile.Mappers;

import com.bank.profile.DTO.AccountDetailsDto;
import com.bank.profile.Entities.AccountDetails;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountDetailsMapper {
    AccountDetailsMapper INSTANCE = Mappers.getMapper(AccountDetailsMapper.class);

    AccountDetailsDto toDTO(AccountDetails accountDetails);

    AccountDetails toEntity(AccountDetailsDto accountDetailsDto);
}
