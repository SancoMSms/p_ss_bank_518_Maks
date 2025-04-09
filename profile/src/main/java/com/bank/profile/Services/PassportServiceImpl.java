package com.bank.profile.Services;

import com.bank.profile.DTO.PassportDto;
import com.bank.profile.Entities.Passport;
import com.bank.profile.Kafka.KafkaErrorProducer;
import com.bank.profile.Mappers.PassportMapper;
import com.bank.profile.Repositories.PassportRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@Setter
public class PassportServiceImpl implements PassportService {
    private static final Logger logger = LoggerFactory.getLogger(PassportServiceImpl.class);

    private final PassportRepository passportRepository;
    private final PassportMapper passportMapper;
    private final KafkaErrorProducer kafkaErrorProducer;
    private final MeterRegistry meterRegistry;

    private Counter createCounter;
    private Counter updateCounter;
    private Counter deleteCounter;
    private Counter errorCounter;

    public PassportServiceImpl(PassportRepository passportRepository,
                               PassportMapper passportMapper,
                               KafkaErrorProducer kafkaErrorProducer,
                               MeterRegistry meterRegistry) {
        this.passportRepository = passportRepository;
        this.passportMapper = passportMapper;
        this.kafkaErrorProducer = kafkaErrorProducer;
        this.meterRegistry = meterRegistry;

        this.createCounter = meterRegistry.counter("passport.create.count");
        this.updateCounter = meterRegistry.counter("passport.update.count");
        this.deleteCounter = meterRegistry.counter("passport.delete.count");
        this.errorCounter = meterRegistry.counter("passport.errors.count");
    }

    @Override
    @Transactional
    public Passport create(@Valid PassportDto passportDto) {
        logger.info("Creating passport: {}", passportDto);
        createCounter.increment();
        try {
            return passportRepository.save(passportMapper.toEntity(passportDto));
        } catch (Exception e) {
            handleError("create", passportDto, e);
            throw e;
        }
    }

    @Override
    @Transactional
    public Passport update(@Valid PassportDto passportDto) {
        logger.info("Updating passport: {}", passportDto);
        updateCounter.increment();
        try {
            return passportRepository.save(passportMapper.toEntity(passportDto));
        } catch (Exception e) {
            handleError("update", passportDto, e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void delete(@NotNull Long id) {
        logger.info("Deleting passport by ID: {}", id);
        deleteCounter.increment();
        try {
            passportRepository.deleteById(id);
        } catch (Exception e) {
            handleError("delete", "ID: " + id, e);
            throw e;
        }
    }

    @Override
    public Passport getPassport(@NotNull Long id) {
        logger.info("Fetching passport by ID: {}", id);
        try {
            return passportRepository.getById(id);
        } catch (Exception e) {
            handleError("getPassport", "ID: " + id, e);
            throw e;
        }
    }

    @Override
    public List<Passport> getAllPassports() {
        logger.info("Fetching all passports");
        try {
            return passportRepository.findAll();
        } catch (Exception e) {
            handleError("getAllPassports", "N/A", e);
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
