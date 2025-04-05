package com.bank.profile.Services;

import com.bank.profile.DTO.AccountDetailsDto;
import com.bank.profile.Entities.AccountDetails;
import com.bank.profile.Mappers.AccountDetailsMapper;
import com.bank.profile.Repositories.AccountDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountDetailsServiceImpl implements AccountDetailsService {
    private final AccountDetailsRepository accountDetailsRepository;
    private final AccountDetailsMapper accountDetailsMapper;

    @Autowired
    public AccountDetailsServiceImpl(AccountDetailsRepository accountDetailsRepository, AccountDetailsMapper accountDetailsMapper) {
        this.accountDetailsRepository = accountDetailsRepository;
        this.accountDetailsMapper = accountDetailsMapper;
    }

    @Override
    public AccountDetails create(AccountDetailsDto accountDetailsDto) {
        return accountDetailsRepository.save(accountDetailsMapper.toEntity(accountDetailsDto));
    }

    @Override
    public AccountDetails update(AccountDetailsDto accountDetailsDto) {
        return accountDetailsRepository.save(accountDetailsMapper.toEntity(accountDetailsDto));
    }

    @Override
    public void delete(Long id) {
        accountDetailsRepository.deleteById(id);
    }

    @Override
    public AccountDetails getAccountDetails(Long id) {
        return accountDetailsRepository.getById(id);
    }

    @Override
    public List<AccountDetails> getAllAccountDetails() {
        return accountDetailsRepository.findAll();
    }
}
