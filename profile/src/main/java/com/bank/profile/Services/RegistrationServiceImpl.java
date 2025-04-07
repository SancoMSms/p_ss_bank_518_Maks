package com.bank.profile.Services;

import com.bank.profile.DTO.RegistrationDto;
import com.bank.profile.Entities.Registration;
import com.bank.profile.Kafka.KafkaErrorProducer;
import com.bank.profile.Mappers.RegistrationMappers;
import com.bank.profile.Repositories.RegistrationRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class RegistrationServiceImpl implements RegistrationService {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationServiceImpl.class);

    private final RegistrationRepository registrationRepository;
    private final RegistrationMappers registrationMappers;
    private final KafkaErrorProducer kafkaErrorProducer;

    private final Counter createCounter;
    private final Counter updateCounter;
    private final Counter deleteCounter;
    private final Counter errorCounter;

    public RegistrationServiceImpl(RegistrationRepository registrationRepository,
                                   RegistrationMappers registrationMappers,
                                   KafkaErrorProducer kafkaErrorProducer,
                                   MeterRegistry meterRegistry) {
        this.registrationRepository = registrationRepository;
        this.registrationMappers = registrationMappers;
        this.kafkaErrorProducer = kafkaErrorProducer;

        this.createCounter = meterRegistry.counter("registration.create.count");
        this.updateCounter = meterRegistry.counter("registration.update.count");
        this.deleteCounter = meterRegistry.counter("registration.delete.count");
        this.errorCounter = meterRegistry.counter("registration.errors.count");
    }

    @Override
    public Registration create(RegistrationDto registrationDto) {
        logger.info("Creating registration: {}", registrationDto);
        createCounter.increment();
        try {
            return registrationRepository.save(registrationMappers.toEntity(registrationDto));
        } catch (Exception e) {
            handleError("create", registrationDto, e);
            throw e;
        }
    }

    @Override
    public Registration update(RegistrationDto registrationDto) {
        logger.info("Updating registration: {}", registrationDto);
        updateCounter.increment();
        try {
            return registrationRepository.save(registrationMappers.toEntity(registrationDto));
        } catch (Exception e) {
            handleError("update", registrationDto, e);
            throw e;
        }
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting registration by ID: {}", id);
        deleteCounter.increment();
        try {
            registrationRepository.deleteById(id);
        } catch (Exception e) {
            handleError("delete", "ID: " + id, e);
            throw e;
        }
    }

    @Override
    public Registration getRegistration(Long id) {
        logger.info("Fetching registration by ID: {}", id);
        try {
            return registrationRepository.getById(id);
        } catch (Exception e) {
            handleError("getRegistration", "ID: " + id, e);
            throw e;
        }
    }

    @Override
    public List<Registration> getAllRegistrations() {
        logger.info("Fetching all registrations");
        try {
            return registrationRepository.findAll();
        } catch (Exception e) {
            handleError("getAllRegistrations", "N/A", e);
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
