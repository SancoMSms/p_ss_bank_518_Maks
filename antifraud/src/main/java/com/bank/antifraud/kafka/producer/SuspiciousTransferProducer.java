package com.bank.antifraud.kafka.producer;

import com.bank.antifraud.dto.TransferAntiFraudDto;

public interface SuspiciousTransferProducer {

    void sendCreateEvent(TransferAntiFraudDto kafkaDto);

    void sendUpdateEvent(TransferAntiFraudDto kafkaDto);

    void sendDeleteEvent(TransferAntiFraudDto kafkaDto);

    void sendGetEvent(String request);
}
