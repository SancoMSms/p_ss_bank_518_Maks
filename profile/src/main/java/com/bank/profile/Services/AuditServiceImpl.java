package com.bank.profile.Services;

import com.bank.profile.Entities.Audit;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String AUDIT_TOPIC = "audit.logs";

    private final List<Audit> auditLogs = new CopyOnWriteArrayList<>();

    @Override
    public void logAuditEvent(String entityType, String operationType, String createdBy, String modifiedBy, String newEntityJson, String entityJson) {
        Audit audit = new Audit();
        audit.setEntityType(entityType);
        audit.setOperationType(operationType);
        audit.setCreatedBy(createdBy);
        audit.setModifiedBy(modifiedBy);
        audit.setCreatedAt(new Date(System.currentTimeMillis()));
        audit.setModifiedAt(new Date(System.currentTimeMillis()));
        audit.setNewEntityJson(newEntityJson);
        audit.setEntityJson(entityJson);

        auditLogs.add(audit);

        kafkaTemplate.send(AUDIT_TOPIC, "Audit Log: " + audit.toString());
    }

    @Override
    public List<Audit> getAllAuditLogs() {
        return List.copyOf(auditLogs);
    }
}
