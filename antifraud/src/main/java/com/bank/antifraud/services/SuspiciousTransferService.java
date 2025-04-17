package com.bank.antifraud.services;

import com.bank.antifraud.dto.AbstractSuspiciousTransferDto;
import com.bank.antifraud.dto.TransferAntiFraudDto;

public interface SuspiciousTransferService {
    AbstractSuspiciousTransferDto createSuspiciousTransfer(TransferAntiFraudDto kafkaDto);
}
