package com.bank.profile.Mappers;

import com.bank.profile.DTO.AccountDetailsDto;
import com.bank.profile.Entities.AccountDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountDetailsMapper {

    AccountDetailsDto toDTO(AccountDetails accountDetails);

    AccountDetails toEntity(AccountDetailsDto accountDetailsDto);
}
