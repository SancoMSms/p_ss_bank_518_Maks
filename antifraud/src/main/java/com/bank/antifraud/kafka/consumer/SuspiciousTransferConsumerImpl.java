package com.bank.antifraud.kafka.consumer;

import com.bank.antifraud.dto.TransferAntiFraudDto;
import com.bank.antifraud.services.SuspiciousTransferService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@RequiredArgsConstructor
@Slf4j
@Validated
public class SuspiciousTransferConsumerImpl implements SuspiciousTransferConsumer {

    private final SuspiciousTransferService suspiciousTransferService;

    @Override
    @KafkaListener(topics = "transfer-to-antifraud", groupId = "antifraud-group")
    public void handleTransfer(@Valid @NotNull TransferAntiFraudDto kafkaDto) {
        log.info("Transfer with transferId: {} received from Kafka", kafkaDto.getTransferId());
        suspiciousTransferService.createSuspiciousTransfer(kafkaDto);
    }
}
