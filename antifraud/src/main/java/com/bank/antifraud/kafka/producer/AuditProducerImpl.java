package com.bank.antifraud.kafka.producer;

import com.bank.antifraud.dto.AuditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class AuditProducerImpl implements AuditProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditProducerImpl.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void sendAuditEvent(AuditDto auditDto) {
        LOGGER.info("Sending audit event: {}", auditDto);
        kafkaTemplate.send("audit-events", auditDto);
        LOGGER.info("Audit event sent successfully");
    }
}
