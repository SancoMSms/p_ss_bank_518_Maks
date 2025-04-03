package com.bank.account.kafka;

import com.bank.account.dto.AuditDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuditProducer {

    private static final String TOPIC_AUDIT = "audit.log";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendAuditEvent(AuditDto auditDto) {
        kafkaTemplate.send(TOPIC_AUDIT, auditDto);
    }
}
