package com.bank.account.services;

import com.bank.account.dto.AccountDto;

import java.util.List;

public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);

    AccountDto updateAccount(Long id, AccountDto accountDto);

    void deleteAccount(Long id);

    List<AccountDto> getAllAccounts();
}
