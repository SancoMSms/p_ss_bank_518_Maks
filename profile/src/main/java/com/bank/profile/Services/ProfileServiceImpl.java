package com.bank.profile.Services;

import com.bank.profile.DTO.ProfileDto;
import com.bank.profile.Entities.Profile;
import com.bank.profile.Kafka.KafkaErrorProducer;
import com.bank.profile.Mappers.ProfileMapper;
import com.bank.profile.Repositories.ProfileRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class ProfileServiceImpl implements ProfileService {
    private static final Logger logger = LoggerFactory.getLogger(ProfileServiceImpl.class);

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final KafkaErrorProducer kafkaErrorProducer;

    private final Counter createCounter;
    private final Counter updateCounter;
    private final Counter deleteCounter;
    private final Counter errorCounter;

    public ProfileServiceImpl(ProfileRepository profileRepository,
                              ProfileMapper profileMapper,
                              KafkaErrorProducer kafkaErrorProducer,
                              MeterRegistry meterRegistry) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
        this.kafkaErrorProducer = kafkaErrorProducer;

        this.createCounter = meterRegistry.counter("profile.create.count");
        this.updateCounter = meterRegistry.counter("profile.update.count");
        this.deleteCounter = meterRegistry.counter("profile.delete.count");
        this.errorCounter = meterRegistry.counter("profile.errors.count");
    }

    @Override
    public Profile create(@Valid ProfileDto profileDto) {
        logger.info("Creating profile: {}", profileDto);
        createCounter.increment();
        try {
            return profileRepository.save(profileMapper.toEntity(profileDto));
        } catch (Exception e) {
            handleError("create", profileDto, e);
            throw e;
        }
    }

    @Override
    public Profile update(@Valid ProfileDto profileDto) {
        logger.info("Updating profile: {}", profileDto);
        updateCounter.increment();
        try {
            return profileRepository.save(profileMapper.toEntity(profileDto));
        } catch (Exception e) {
            handleError("update", profileDto, e);
            throw e;
        }
    }

    @Override
    public void delete(@NotNull Long id) {
        logger.info("Deleting profile by ID: {}", id);
        deleteCounter.increment();
        try {
            profileRepository.deleteById(id);
        } catch (Exception e) {
            handleError("delete", "ID: " + id, e);
            throw e;
        }
    }

    @Override
    public Profile getProfile(@NotNull Long id) {
        logger.info("Fetching profile by ID: {}", id);
        try {
            return profileRepository.getById(id);
        } catch (Exception e) {
            handleError("getProfile", "ID: " + id, e);
            throw e;
        }
    }

    @Override
    public List<Profile> getAllProfiles() {
        logger.info("Fetching all profiles");
        try {
            return profileRepository.findAll();
        } catch (Exception e) {
            handleError("getAllProfiles", "N/A", e);
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
