package com.bank.antifraud.kafka.producer;

import com.bank.antifraud.dto.AuditDto;

public interface AuditProducer {

    void sendAuditEvent(AuditDto auditDto);
}