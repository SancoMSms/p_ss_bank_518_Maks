package com.bank.account.kafka;

import com.bank.account.dto.AccountDto;
import com.bank.account.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountConsumer {

    private final AccountService accountService;

    @KafkaListener(topics = "account.create", groupId = "account-group")
    public void consumeCreateEvent(AccountDto accountDto) {
        try {
            log.info("Получено событие создания аккаунта. Passport ID: {}", accountDto.getPassportId());
            accountService.createAccount(accountDto);
        } catch (Exception e) {
            log.info("Не удалось обработать событие создания аккаунта. Passport ID: {}, Ошибка: {}",
                    accountDto.getPassportId(), e.getMessage());
            throw e;
        }
    }

    @KafkaListener(topics = "account.update", groupId = "account-group")
    public void consumeUpdateEvent(AccountDto accountDto) {
        try {
            log.info("Получено событие обновления аккаунта. ID: {}", accountDto.getId());
            accountService.updateAccount(accountDto.getId(), accountDto);
        } catch (Exception e) {
            log.info("Не удалось обработать событие обновления аккаунта. ID: {}, Ошибка: {}",
                    accountDto.getId(), e.getMessage());
            throw e;
        }
    }

    @KafkaListener(topics = "account.delete", groupId = "account-group")
    public void consumeDeleteEvent(Long accountId) {
        try {
            log.info("Получено событие удаления аккаунта. ID: {}", accountId);
            accountService.deleteAccount(accountId);
        } catch (Exception e) {
            log.info("Не удалось обработать событие удаления аккаунта. ID: {}, Ошибка: {}",
                    accountId, e.getMessage());
            throw e;
        }
    }
}
