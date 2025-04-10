package com.bank.antifraud.services;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.dto.SuspiciousTransferDto;

public interface AuditService {

    void logAudit(SuspiciousTransferDto suspiciousTransferDto, String operation_type);
    AuditDto getDtoById(Long id);
}
