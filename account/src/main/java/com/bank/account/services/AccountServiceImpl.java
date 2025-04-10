package com.bank.account.services;

import com.bank.account.dto.AccountDto;
import com.bank.account.entities.Account;
import com.bank.account.kafka.AccountProducer;
import com.bank.account.mappers.AccountMapper;
import com.bank.account.repositories.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final AccountProducer accountProducer;

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        try {
            final Account account = accountMapper.toEntity(accountDto);
            final Account savedAccount = accountRepository.save(account);
            log.info("Аккаунт успешно создан. ID: {}, Passport ID: {}",
                    savedAccount.getId(), savedAccount.getPassportId());
            accountProducer.sendCreateEvent(accountMapper.toDto(savedAccount));
            return accountMapper.toDto(savedAccount);
        } catch (Exception e) {
            log.info("Не удалось создать аккаунт. Passport ID: {}, Ошибка: {}",
                    accountDto.getPassportId(), e.getMessage());
            throw e;
        }
    }

    @Override
    public AccountDto updateAccount(Long id, AccountDto accountDto) {
        try {
            final Account existingAccount = accountRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Аккаунт не найден с ID: " + id));

            existingAccount.setPassportId(accountDto.getPassportId());
            existingAccount.setAccountNumber(accountDto.getAccountNumber());
            existingAccount.setBankDetailsId(accountDto.getBankDetailsId());
            existingAccount.setMoney(accountDto.getMoney());
            existingAccount.setNegativeBalance(accountDto.getNegativeBalance());
            existingAccount.setProfileId(accountDto.getProfileId());

            final Account updatedAccount = accountRepository.save(existingAccount);
            log.info("Аккаунт успешно обновлен. ID: {}, Новый баланс: {}",
                    updatedAccount.getId(), updatedAccount.getMoney());
            accountProducer.sendUpdateEvent(accountMapper.toDto(updatedAccount));
            return accountMapper.toDto(updatedAccount);
        } catch (Exception e) {
            log.info("Не удалось обновить аккаунт. ID: {}, Ошибка: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteAccount(Long id) {
        try {
            if (!accountRepository.existsById(id)) {
                final String message = "Аккаунт не существует. ID: " + id;
                log.error(message);
                throw new EntityNotFoundException(message);
            }
            accountProducer.sendDeleteEvent(id);
            accountRepository.deleteById(id);
            log.info("Аккаунт успешно удален. ID: {}", id);
        } catch (Exception e) {
            log.info("Не удалось удалить аккаунт. ID: {}, Ошибка: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        try {
            final List<Account> accounts = accountRepository.findAll();
            log.info("Получены все аккаунты. Общее количество: {}", accounts.size());
            return accountMapper.toListAccountDto(accounts);
        } catch (Exception e) {
            log.info("Не удалось получить список аккаунтов. Ошибка: {}", e.getMessage());
            throw e;
        }
    }
}
