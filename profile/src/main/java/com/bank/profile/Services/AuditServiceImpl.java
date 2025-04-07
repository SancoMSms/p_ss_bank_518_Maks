package com.bank.profile.Services;

import com.bank.profile.Entities.Audit;
import com.bank.profile.Kafka.KafkaErrorProducer;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private static final Logger logger = LoggerFactory.getLogger(AuditServiceImpl.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaErrorProducer kafkaErrorProducer;

    private final MeterRegistry meterRegistry;

    private static final String AUDIT_TOPIC = "audit.logs";

    private final List<Audit> auditLogs = new CopyOnWriteArrayList<>();

    private final Counter auditLogCounter;
    private final Counter errorCounter;

    @Autowired
    public AuditServiceImpl(KafkaTemplate<String, String> kafkaTemplate,
                            KafkaErrorProducer kafkaErrorProducer,
                            MeterRegistry meterRegistry) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaErrorProducer = kafkaErrorProducer;
        this.meterRegistry = meterRegistry;

        this.auditLogCounter = meterRegistry.counter("audit.log.count");
        this.errorCounter = meterRegistry.counter("audit.errors.count");
    }

    @Override
    public void logAuditEvent(
            @NotNull @Size(min = 1, max = 100) String entityType,
            @NotNull @Size(min = 1, max = 100) String operationType,
            @NotNull @Size(min = 1, max = 100) String createdBy,
            @NotNull @Size(min = 1, max = 100) String modifiedBy,
            @NotNull @Size(min = 1, max = 500) String newEntityJson,
            @NotNull @Size(min = 1, max = 500) String entityJson) {

        logger.info("Logging audit event: EntityType: {}, OperationType: {}, CreatedBy: {}, ModifiedBy: {}, NewEntityJson: {}, EntityJson: {}",
                entityType, operationType, createdBy, modifiedBy, newEntityJson, entityJson);

        try {
            // Создание нового аудита
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

            auditLogCounter.increment();
            logger.info("Successfully logged audit event: {}", audit);
        } catch (Exception e) {
            logger.error("Error during logging audit event: {}", e.getMessage(), e);

            errorCounter.increment();

            kafkaErrorProducer.sendError("Error logging audit event: " + e.getMessage());
        }
    }

    @Override
    public List<Audit> getAllAuditLogs() {
        logger.info("Fetching all audit logs.");

        try {
            return List.copyOf(auditLogs);
        } catch (Exception e) {
            logger.error("Error fetching all audit logs: {}", e.getMessage(), e);

            errorCounter.increment();

            kafkaErrorProducer.sendError("Error fetching audit logs: " + e.getMessage());
            return List.of();
        }
    }
}
