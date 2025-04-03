package com.bank.account.services;

import com.bank.account.dto.AuditDto;

public interface AuditService {

    void saveAudit(AuditDto auditDto);
}
