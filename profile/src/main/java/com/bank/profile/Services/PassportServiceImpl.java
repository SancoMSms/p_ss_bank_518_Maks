package com.bank.profile.Services;

import com.bank.profile.DTO.PassportDto;
import com.bank.profile.Entities.Passport;
import com.bank.profile.Kafka.KafkaErrorProducer;
import com.bank.profile.Mappers.PassportMapper;
import com.bank.profile.Repositories.PassportRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class PassportServiceImpl implements PassportService {
    private static final Logger logger = LoggerFactory.getLogger(PassportServiceImpl.class);

    private final PassportRepository passportRepository;
    private final PassportMapper passportMapper;
    private final KafkaErrorProducer kafkaErrorProducer;

    private final Counter createCounter;
    private final Counter updateCounter;
    private final Counter deleteCounter;
    private final Counter errorCounter;

    public PassportServiceImpl(PassportRepository passportRepository,
                               PassportMapper passportMapper,
                               KafkaErrorProducer kafkaErrorProducer,
                               MeterRegistry meterRegistry) {
        this.passportRepository = passportRepository;
        this.passportMapper = passportMapper;
        this.kafkaErrorProducer = kafkaErrorProducer;

        this.createCounter = meterRegistry.counter("passport.create.count");
        this.updateCounter = meterRegistry.counter("passport.update.count");
        this.deleteCounter = meterRegistry.counter("passport.delete.count");
        this.errorCounter = meterRegistry.counter("passport.errors.count");
    }

    @Override
    public Passport create(PassportDto passportDto) {
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
    public Passport update(PassportDto passportDto) {
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
    public void delete(Long id) {
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
    public Passport getPassport(Long id) {
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
