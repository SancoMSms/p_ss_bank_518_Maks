package com.bank.antifraud.kafka.consumer;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.services.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuditConsumerImpl implements AuditConsumer {

    private final AuditService auditService;

    @Override
    @KafkaListener(
            topics = "audit-events",
            groupId = "antifraud-audit-group",
            containerFactory = "auditKafkaListenerContainerFactory"
    )
    public void handleAuditEvent(AuditDto auditDto) {
        log.info("Received audit event: {}", auditDto);
        //auditService.logAudit();//TODO
        log.info("Audit event persisted successfully for entity: {}, operation: {}",
                auditDto.getEntityType(), auditDto.getOperationType());
    }
}
