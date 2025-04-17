package com.bank.antifraud.kafka.consumer;

import com.bank.antifraud.dto.TransferAntiFraudDto;

public interface SuspiciousTransferConsumer {

    void handleTransfer(TransferAntiFraudDto kafkaDto);
}
