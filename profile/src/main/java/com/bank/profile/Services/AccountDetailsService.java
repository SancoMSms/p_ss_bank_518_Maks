package com.bank.profile.Services;

import com.bank.profile.Entities.AccountDetails;
import java.util.List;
import java.util.Optional;

public interface AccountDetailsService {
    AccountDetails createAccountDetails(AccountDetails accountDetails);
    Optional<AccountDetails> getAccountDetailsById(Long id);
    List<AccountDetails> getAllAccountDetails();
    AccountDetails updateAccountDetails(Long id, AccountDetails accountDetails);
    void deleteAccountDetails(Long id);
}
