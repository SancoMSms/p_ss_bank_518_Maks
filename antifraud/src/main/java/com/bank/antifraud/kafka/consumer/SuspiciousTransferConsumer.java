package com.bank.antifraud.kafka.consumer;

import com.bank.antifraud.dto.TransferAntiFraudDto;

public interface SuspiciousTransferConsumer {

    void handleCreateEvent(TransferAntiFraudDto kafkaDto);

    void handleUpdateEvent(TransferAntiFraudDto kafkaDto);

    void handleDeleteEvent(TransferAntiFraudDto kafkaDto);

    void handleGetEvent(String request);
}
