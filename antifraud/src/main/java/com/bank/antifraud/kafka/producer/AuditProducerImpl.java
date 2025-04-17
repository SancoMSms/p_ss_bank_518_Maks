package com.bank.antifraud.kafka.producer;

import com.bank.antifraud.dto.AuditDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuditProducerImpl implements AuditProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void sendAuditEvent(AuditDto auditDto) {
        log.info("Sending audit event: {}", auditDto);
        kafkaTemplate.send("audit-events", auditDto);
        log.info("Audit event sent successfully");
    }
}
