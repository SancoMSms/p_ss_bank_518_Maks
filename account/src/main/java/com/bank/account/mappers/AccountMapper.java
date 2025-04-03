package com.bank.account.mappers;

import com.bank.account.dto.AccountDto;
import com.bank.account.entities.Account;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountMapper {

    List<AccountDto> toListAccountDto (List<Account> accountList);
    AccountDto toDto(Account account);
    Account toEntity (AccountDto accountDto);
}
