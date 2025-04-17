package com.bank.antifraud.kafka.producer;

import com.bank.antifraud.dto.TransferAntiFraudDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SuspiciousTransferProducerImpl implements SuspiciousTransferProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void sendApprovedEvent(TransferAntiFraudDto kafkaDto) {
        log.info("Sending approve event for suspicious transfer with ID: {}",
                kafkaDto.getTransferId());
        kafkaTemplate.send("suspicious-transfers.approved",
                kafkaDto.getEntityType().name(), kafkaDto);
        log.info("Approve event sent successfully");
    }

    @Override
    public void sendBlockedEvent(TransferAntiFraudDto kafkaDto) {
        log.info("Sending block event for suspicious transfer with ID: {}",
                kafkaDto.getTransferId());
        kafkaTemplate.send("suspicious-transfers.blocked",
                kafkaDto.getTransferId());
        log.info("Block event sent successfully");
    }
}
