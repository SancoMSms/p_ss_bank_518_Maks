package com.bank.antifraud.kafka.consumer;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.services.AuditService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuditConsumerImpl implements AuditConsumer {

    private static final Logger logger = LoggerFactory.getLogger(AuditConsumerImpl.class);

    private final AuditService auditService;

    @Override
    @KafkaListener(topics = "audit-events", groupId = "antifraud-audit-group")
    public void handleAuditEvent(AuditDto auditDto) {
        logger.info("Received audit event: {}", auditDto);
        //auditService.logAudit();//TODO
        logger.info("Processed audit event successfully");
    }
}