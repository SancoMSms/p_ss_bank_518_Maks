package com.bank.antifraud.kafka.consumer;

import com.bank.antifraud.dto.TransferAntiFraudDto;
import com.bank.antifraud.services.SuspiciousTransferService;
import com.bank.antifraud.util.EventTracker;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SuspiciousTransferConsumerImpl implements SuspiciousTransferConsumer {

    private static final Logger logger = LoggerFactory.getLogger(SuspiciousTransferConsumerImpl.class);
    private final SuspiciousTransferService suspiciousTransferService;
    private final EventTracker eventTracker;

    @Override
    @KafkaListener(topics = "suspicious-transfers.create", groupId = "antifraud-group")
    public void handleCreateEvent(TransferAntiFraudDto kafkaDto) {
        Long transfer_id = kafkaDto.getTransferId();
        if (transfer_id == null || eventTracker.isProcessed(transfer_id)) {
            logger.info("Ignoring create event with id: {}", transfer_id);
            return;
        }
        eventTracker.markAsProcessed(transfer_id);
        logger.info("Received create event for suspicious transferDto: {}", kafkaDto);
        suspiciousTransferService.createSuspiciousTransfer(kafkaDto);
        logger.info("Processed create event successfully");
    }

    @Override
    @KafkaListener(topics = "suspicious-transfers.update", groupId = "antifraud-group")
    public void handleUpdateEvent(TransferAntiFraudDto kafkaDto) {
        Long transferId = kafkaDto.getTransferId();
        if (transferId == null || eventTracker.isProcessed(transferId)) {
            logger.info("Ignoring update event with id: {}", transferId);
            return;
        }
        eventTracker.markAsProcessed(transferId);
        logger.info("Received update event for suspicious kafkaDto: {}", kafkaDto);
        suspiciousTransferService.updateSuspiciousTransfer(kafkaDto);
        logger.info("Processed update event successfully");
    }

    @Override
    @KafkaListener(topics = "suspicious-transfers.delete", groupId = "antifraud-group")
    public void handleDeleteEvent(TransferAntiFraudDto kafkaDto) {
        Long transactionId = kafkaDto.getTransferId();
        if (transactionId == null || eventTracker.isProcessed(transactionId)) {
            logger.info("Ignoring delete event with id: {}", transactionId);
            return;
        }
        eventTracker.markAsProcessed(transactionId);
        logger.info("Received delete event for suspicious transfer with ID: {}", transactionId);
        suspiciousTransferService.deleteSuspiciousTransfer(kafkaDto);
        logger.info("Processed delete event successfully");
    }

    @Override
    @KafkaListener(topics = "suspicious-transfers.get", groupId = "antifraud-group")
    public void handleGetEvent(String request) {
        logger.info("Received get event for suspicious transfers: {}", request);
        logger.info("Processed get event successfully");
    }
}