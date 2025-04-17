package com.bank.antifraud.services;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.dto.AbstractSuspiciousTransferDto;

public interface AuditService {

    void logAudit(AbstractSuspiciousTransferDto suspiciousTransferDto);
    AuditDto getDtoById(Long id);
}
