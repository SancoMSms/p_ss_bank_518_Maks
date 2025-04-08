package com.bank.antifraud.services;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.dto.SuspiciousTransferDto;
import com.bank.antifraud.dto.TransferAntiFraudDto;

public interface SuspiciousTransferService {
    SuspiciousTransferDto createSuspiciousTransfer(TransferAntiFraudDto kafkaDto);

    SuspiciousTransferDto updateSuspiciousTransfer(TransferAntiFraudDto kafkaDto);

    void deleteSuspiciousTransfer(TransferAntiFraudDto kafkaDto);
}
