package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.AuditDto;

import java.util.List;

public interface AuditService {

    AuditDto addAudit(AuditDto auditDto);

    List<AuditDto> getAllAudits();

    AuditDto getAuditById(Long id);

    AuditDto updateAudit(AuditDto auditDto);

}