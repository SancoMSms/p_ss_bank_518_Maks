package com.bank.profile.Kafka;

import com.bank.profile.DTO.ProfileDto;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ProfileConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ProfileConsumer.class);

    private final Counter createCounter;
    private final Counter updateCounter;
    private final Counter deleteCounter;
    private final Counter getCounter;
    private final Counter errorCounter;

    public ProfileConsumer(MeterRegistry meterRegistry) {
        this.createCounter = meterRegistry.counter("profile.create.receive.count");
        this.updateCounter = meterRegistry.counter("profile.update.receive.count");
        this.deleteCounter = meterRegistry.counter("profile.delete.receive.count");
        this.getCounter = meterRegistry.counter("profile.get.receive.count");
        this.errorCounter = meterRegistry.counter("profile.errors.receive.count");
    }

    @KafkaListener(topics = "${spring.kafka.topics.profile-create}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeCreate(ConsumerRecord<String, ProfileDto> record) {
        logger.info("Received create profile: {}", record.value());
        createCounter.increment();
        try {
            logger.info("Processed create profile: {}", record.value());
        } catch (Exception e) {
            logger.error("Error processing create profile: {}", record.value(), e);
            errorCounter.increment();
        }
    }

    @KafkaListener(topics = "${spring.kafka.topics.profile-update}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeUpdate(ConsumerRecord<String, ProfileDto> record) {
        logger.info("Received update profile: {}", record.value());
        updateCounter.increment();
        try {
            logger.info("Processed update profile: {}", record.value());
        } catch (Exception e) {
            logger.error("Error processing update profile: {}", record.value(), e);
            errorCounter.increment();
        }
    }

    @KafkaListener(topics = "${spring.kafka.topics.profile-delete}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeDelete(ConsumerRecord<String, ProfileDto> record) {
        logger.info("Received delete profile: {}", record.value());
        deleteCounter.increment();
        try {
            logger.info("Processed delete profile: {}", record.value());
        } catch (Exception e) {
            logger.error("Error processing delete profile: {}", record.value(), e);
            errorCounter.increment();
        }
    }

    @KafkaListener(topics = "${spring.kafka.topics.profile-get}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeGet(ConsumerRecord<String, ProfileDto> record) {
        logger.info("Received get profile: {}", record.value());
        getCounter.increment();
        try {
            logger.info("Processed get profile: {}", record.value());
        } catch (Exception e) {
            logger.error("Error processing get profile: {}", record.value(), e);
            errorCounter.increment();
        }
    }
}
