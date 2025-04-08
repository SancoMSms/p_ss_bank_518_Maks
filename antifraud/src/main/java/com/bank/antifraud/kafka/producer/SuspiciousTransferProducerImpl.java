package com.bank.antifraud.kafka.producer;

import com.bank.antifraud.dto.SuspiciousTransferDto;
import com.bank.antifraud.dto.TransferAntiFraudDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class SuspiciousTransferProducerImpl implements SuspiciousTransferProducer {

    private static final Logger logger = LoggerFactory.getLogger(SuspiciousTransferProducerImpl.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void sendCreateEvent(TransferAntiFraudDto kafkaDto) {
        logger.info("Sending create event for suspicious transferDto of type {}: {}", kafkaDto.getEntityType(), kafkaDto);
        kafkaTemplate.send("suspicious-transfers.create", kafkaDto.getEntityType(), kafkaDto);
        logger.info("Create event sent successfully");
    }

    @Override
    public void sendUpdateEvent(TransferAntiFraudDto kafkaDto) {
        logger.info("Sending update event for suspicious transferDto of type {}: {}", kafkaDto.getEntityType(), kafkaDto);
        kafkaTemplate.send("suspicious-transfers.update", kafkaDto.getEntityType(), kafkaDto);
        logger.info("Update event sent successfully");
    }

    @Override
    public void sendDeleteEvent(TransferAntiFraudDto kafkaDto) {
        logger.info("Sending delete event for suspicious transfer with ID: {}", kafkaDto.getTransferId());
        kafkaTemplate.send("suspicious-transfers.delete", "DELETE", kafkaDto.getTransferId());
        logger.info("Delete event sent successfully");
    }

    @Override
    public void sendGetEvent(String request) {
        logger.info("Sending get event for suspicious transfers: {}", request);
        kafkaTemplate.send("suspicious-transfers.get", "GET", request);
        logger.info("Get event sent successfully");
    }
}