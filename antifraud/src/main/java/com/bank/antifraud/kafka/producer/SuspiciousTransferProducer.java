package com.bank.antifraud.kafka.producer;

import com.bank.antifraud.dto.TransferAntiFraudDto;

public interface SuspiciousTransferProducer {

    void sendApprovedEvent(TransferAntiFraudDto kafkaDto);

    void sendBlockedEvent(TransferAntiFraudDto kafkaDto);
}
