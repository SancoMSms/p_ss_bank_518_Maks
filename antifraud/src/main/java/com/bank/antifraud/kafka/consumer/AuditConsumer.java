package com.bank.antifraud.kafka.consumer;

import com.bank.antifraud.dto.AuditDto;

public interface AuditConsumer {

    void handleAuditEvent(AuditDto auditDto);
}