package com.bank.profile.Kafka;

import com.bank.profile.DTO.ProfileDto;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProfileProducer {

    private static final Logger logger = LoggerFactory.getLogger(ProfileProducer.class);

    @Value("${spring.kafka.topics.profile-create}")
    private String profileCreate;
    @Value("${spring.kafka.topics.profile-update}")
    private String profileUpdate;
    @Value("${spring.kafka.topics.profile-delete}")
    private String profileDelete;
    @Value("${spring.kafka.topics.profile-get}")
    private String profileGet;

    private final KafkaTemplate<String, ProfileDto> kafkaTemplate;
    private final Counter createCounter;
    private final Counter updateCounter;
    private final Counter deleteCounter;
    private final Counter getCounter;
    private final Counter errorCounter;

    public ProfileProducer(KafkaTemplate<String, ProfileDto> kafkaTemplate, MeterRegistry meterRegistry) {
        this.kafkaTemplate = kafkaTemplate;
        this.createCounter = meterRegistry.counter("profile.create.count");
        this.updateCounter = meterRegistry.counter("profile.update.count");
        this.deleteCounter = meterRegistry.counter("profile.delete.count");
        this.getCounter = meterRegistry.counter("profile.get.count");
        this.errorCounter = meterRegistry.counter("profile.errors.count");
    }

    public void sendCreateProfile(ProfileDto profile) {
        logger.info("Sending create profile message: {}", profile);
        createCounter.increment();
        try {
            kafkaTemplate.send(profileCreate, profile);
            logger.info("Successfully sent create profile message: {}", profile);
        } catch (Exception e) {
            logger.error("Error sending create profile message: {}", profile, e);
            errorCounter.increment();
        }
    }

    public void sendUpdateProfile(ProfileDto profile) {
        logger.info("Sending update profile message: {}", profile);
        updateCounter.increment();
        try {
            kafkaTemplate.send(profileUpdate, profile);
            logger.info("Successfully sent update profile message: {}", profile);
        } catch (Exception e) {
            logger.error("Error sending update profile message: {}", profile, e);
            errorCounter.increment();
        }
    }

    public void sendDeleteProfile(ProfileDto profile) {
        logger.info("Sending delete profile message: {}", profile);
        deleteCounter.increment();
        try {
            kafkaTemplate.send(profileDelete, profile);
            logger.info("Successfully sent delete profile message: {}", profile);
        } catch (Exception e) {
            logger.error("Error sending delete profile message: {}", profile, e);
            errorCounter.increment();
        }
    }

    public void sendGetProfile(ProfileDto profile) {
        logger.info("Sending get profile message: {}", profile);
        getCounter.increment();
        try {
            kafkaTemplate.send(profileGet, profile);
            logger.info("Successfully sent get profile message: {}", profile);
        } catch (Exception e) {
            logger.error("Error sending get profile message: {}", profile, e);
            errorCounter.increment();
        }
    }
}
