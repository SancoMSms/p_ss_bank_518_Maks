package com.bank.profile.Services;

import com.bank.profile.DTO.AccountDetailsDto;
import com.bank.profile.Entities.AccountDetails;
import com.bank.profile.Kafka.KafkaErrorProducer;
import com.bank.profile.Mappers.AccountDetailsMapper;
import com.bank.profile.Repositories.AccountDetailsRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountDetailsServiceImpl implements AccountDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(AccountDetailsServiceImpl.class);

    private final AccountDetailsRepository accountDetailsRepository;
    private final AccountDetailsMapper accountDetailsMapper;
    private final KafkaErrorProducer kafkaErrorProducer;

    private final Counter createCounter;
    private final Counter updateCounter;
    private final Counter deleteCounter;
    private final Counter errorCounter;

    public AccountDetailsServiceImpl(AccountDetailsRepository accountDetailsRepository,
                                     AccountDetailsMapper accountDetailsMapper,
                                     KafkaErrorProducer kafkaErrorProducer,
                                     MeterRegistry meterRegistry) {
        this.accountDetailsRepository = accountDetailsRepository;
        this.accountDetailsMapper = accountDetailsMapper;
        this.kafkaErrorProducer = kafkaErrorProducer;

        this.createCounter = meterRegistry.counter("accountDetails.create.count");
        this.updateCounter = meterRegistry.counter("accountDetails.update.count");
        this.deleteCounter = meterRegistry.counter("accountDetails.delete.count");
        this.errorCounter = meterRegistry.counter("accountDetails.errors.count");
    }

    @Override
    public AccountDetails create(@Valid @NotNull AccountDetailsDto accountDetailsDto) {
        logger.info("Creating account details: {}", accountDetailsDto);
        createCounter.increment();
        try {
            return accountDetailsRepository.save(accountDetailsMapper.toEntity(accountDetailsDto));
        } catch (Exception e) {
            handleError("create", accountDetailsDto, e);
            throw e;
        }
    }

    @Override
    public AccountDetails update(@Valid @NotNull AccountDetailsDto accountDetailsDto) {
        logger.info("Updating account details: {}", accountDetailsDto);
        updateCounter.increment();
        try {
            return accountDetailsRepository.save(accountDetailsMapper.toEntity(accountDetailsDto));
        } catch (Exception e) {
            handleError("update", accountDetailsDto, e);
            throw e;
        }
    }

    @Override
    public void delete(@NotNull Long id) {
        logger.info("Deleting account details by ID: {}", id);
        deleteCounter.increment();
        try {
            accountDetailsRepository.deleteById(id);
        } catch (Exception e) {
            handleError("delete", "ID: " + id, e);
            throw e;
        }
    }

    @Override
    public AccountDetails getAccountDetails(@NotNull Long id) {
        logger.info("Fetching account details by ID: {}", id);
        try {
            return accountDetailsRepository.getById(id);
        } catch (Exception e) {
            handleError("getAccountDetails", "ID: " + id, e);
            throw e;
        }
    }

    @Override
    public List<AccountDetails> getAllAccountDetails() {
        logger.info("Fetching all account details");
        try {
            return accountDetailsRepository.findAll();
        } catch (Exception e) {
            handleError("getAllAccountDetails", "N/A", e);
            throw e;
        }
    }

    private void handleError(String operation, Object data, Exception e) {
        logger.error("Error during {} with data {}: {}", operation, data, e.getMessage(), e);
        errorCounter.increment();
        kafkaErrorProducer.sendError(String.format("Operation '%s' failed. Data: %s. Error: %s",
                operation, data, e.getMessage()));
    }
}
