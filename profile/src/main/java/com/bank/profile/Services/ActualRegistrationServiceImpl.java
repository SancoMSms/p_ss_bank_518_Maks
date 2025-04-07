package com.bank.profile.Services;

import com.bank.profile.DTO.ActualRegistrationDto;
import com.bank.profile.Entities.ActualRegistration;
import com.bank.profile.Kafka.KafkaErrorProducer;
import com.bank.profile.Mappers.ActualRegistrationMapper;
import com.bank.profile.Repositories.ActualRegistrationRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class ActualRegistrationServiceImpl implements ActualRegistrationService {
    private static final Logger logger = LoggerFactory.getLogger(ActualRegistrationServiceImpl.class);

    private final ActualRegistrationRepository actualRegistrationRepository;
    private final ActualRegistrationMapper actualRegistrationMapper;
    private final KafkaErrorProducer kafkaErrorProducer;

    private final Counter createCounter;
    private final Counter updateCounter;
    private final Counter deleteCounter;
    private final Counter errorCounter;

    public ActualRegistrationServiceImpl(ActualRegistrationRepository actualRegistrationRepository,
                                         ActualRegistrationMapper actualRegistrationMapper,
                                         KafkaErrorProducer kafkaErrorProducer,
                                         MeterRegistry meterRegistry) {
        this.actualRegistrationRepository = actualRegistrationRepository;
        this.actualRegistrationMapper = actualRegistrationMapper;
        this.kafkaErrorProducer = kafkaErrorProducer;

        this.createCounter = meterRegistry.counter("actualRegistration.create.count");
        this.updateCounter = meterRegistry.counter("actualRegistration.update.count");
        this.deleteCounter = meterRegistry.counter("actualRegistration.delete.count");
        this.errorCounter = meterRegistry.counter("actualRegistration.errors.count");
    }

    @Override
    public ActualRegistration create(ActualRegistrationDto actualRegistrationDto) {
        logger.info("Creating actual registration: {}", actualRegistrationDto);
        createCounter.increment();
        try {
            return actualRegistrationRepository.save(actualRegistrationMapper.toEntity(actualRegistrationDto));
        } catch (Exception e) {
            handleError("create", actualRegistrationDto, e);
            throw e;
        }
    }

    @Override
    public ActualRegistration update(ActualRegistrationDto actualRegistrationDto) {
        logger.info("Updating actual registration: {}", actualRegistrationDto);
        updateCounter.increment();
        try {
            return actualRegistrationRepository.save(actualRegistrationMapper.toEntity(actualRegistrationDto));
        } catch (Exception e) {
            handleError("update", actualRegistrationDto, e);
            throw e;
        }
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting actual registration by ID: {}", id);
        deleteCounter.increment();
        try {
            actualRegistrationRepository.deleteById(id);
        } catch (Exception e) {
            handleError("delete", "ID: " + id, e);
            throw e;
        }
    }

    @Override
    public ActualRegistration getActualRegistration(Long id) {
        logger.info("Fetching actual registration by ID: {}", id);
        try {
            return actualRegistrationRepository.getById(id);
        } catch (Exception e) {
            handleError("getActualRegistration", "ID: " + id, e);
            throw e;
        }
    }

    @Override
    public List<ActualRegistration> getAllActualRegistrations() {
        logger.info("Fetching all actual registrations");
        try {
            return actualRegistrationRepository.findAll();
        } catch (Exception e) {
            handleError("getAllActualRegistrations", "N/A", e);
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
