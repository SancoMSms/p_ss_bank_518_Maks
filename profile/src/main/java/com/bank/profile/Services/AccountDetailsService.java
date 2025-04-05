package com.bank.profile.Services;

import com.bank.profile.DTO.AccountDetailsDto;
import com.bank.profile.Entities.AccountDetails;

import java.util.List;

public interface AccountDetailsService {

    AccountDetails create(AccountDetailsDto accountDetailsDto);

    AccountDetails update(AccountDetailsDto accountDetailsDto);

    void delete(Long id);

    AccountDetails getAccountDetails(Long id);

    List<AccountDetails> getAllAccountDetails();
}
