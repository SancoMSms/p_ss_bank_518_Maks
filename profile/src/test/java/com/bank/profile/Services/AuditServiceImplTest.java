package com.bank.profile.Services;

import com.bank.profile.Entities.Audit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.kafka.core.KafkaTemplate;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuditServiceImplTest {

    private static final String AUDIT_TOPIC = "audit.logs";

    @Mock
    private final KafkaTemplate<String, String> kafkaTemplate = mock(KafkaTemplate.class);

    private AuditServiceImpl auditService;

    @BeforeEach
    public void setup() {
        auditService = new AuditServiceImpl(kafkaTemplate);
    }

    @Test
    public void testLogAuditEvent() {
        String entityType = "USER";
        String operationType = "CREATE";
        String createdBy = "admin";
        String modifiedBy = "admin";
        String newEntityJson = "{\"name\":\"John\"}";
        String entityJson = "{\"name\":\"Doe\"}";

        when(kafkaTemplate.send(eq(AUDIT_TOPIC), anyString())).thenReturn(null);

        auditService.logAuditEvent(entityType, operationType, createdBy, modifiedBy, newEntityJson, entityJson);

        List<Audit> auditLogs = auditService.getAllAuditLogs();
        assertEquals(1, auditLogs.size());

        Audit audit = auditLogs.get(0);
        assertEquals(entityType, audit.getEntityType());
        assertEquals(operationType, audit.getOperationType());
        assertEquals(createdBy, audit.getCreatedBy());
        assertEquals(modifiedBy, audit.getModifiedBy());
        assertEquals(newEntityJson, audit.getNewEntityJson());
        assertEquals(entityJson, audit.getEntityJson());

        verify(kafkaTemplate, times(1)).send(eq(AUDIT_TOPIC), anyString());
    }

    @Test
    public void testGetAllAuditLogs() {
        auditService.logAuditEvent("USER", "CREATE", "admin", "admin", "{\"name\":\"John\"}", "{\"name\":\"Doe\"}");
        auditService.logAuditEvent("USER", "UPDATE", "admin", "admin", "{\"name\":\"John Updated\"}", "{\"name\":\"Doe Updated\"}");

        List<Audit> auditLogs = auditService.getAllAuditLogs();
        assertEquals(2, auditLogs.size());
    }
}
