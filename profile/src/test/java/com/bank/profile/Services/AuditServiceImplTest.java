package com.bank.profile.Services;

import com.bank.profile.Entities.Audit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class AuditServiceImplTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    private AuditServiceImpl auditService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        auditService = new AuditServiceImpl(kafkaTemplate);
    }

    @Test
    public void testLogAuditEvent() {
        // Подготовка данных для логирования
        String entityType = "User";
        String operationType = "Create";
        String createdBy = "admin";
        String modifiedBy = "admin";
        String newEntityJson = "{\"name\":\"John\"}";
        String entityJson = "{\"name\":\"Doe\"}";

        // Мокаем send() для имитации успешной отправки
        // Мы возвращаем null, как будто операция завершена успешно
        when(kafkaTemplate.send(eq("audit.logs"), anyString())).thenReturn(null);

        // Вызываем метод
        auditService.logAuditEvent(entityType, operationType, createdBy, modifiedBy, newEntityJson, entityJson);

        // Проверяем, что в списке auditLogs появилась новая запись
        List<Audit> auditLogs = auditService.getAllAuditLogs();
        assertEquals(1, auditLogs.size());

        Audit audit = auditLogs.get(0);
        assertEquals(entityType, audit.getEntityType());
        assertEquals(operationType, audit.getOperationType());
        assertEquals(createdBy, audit.getCreatedBy());
        assertEquals(modifiedBy, audit.getModifiedBy());
        assertEquals(newEntityJson, audit.getNewEntityJson());
        assertEquals(entityJson, audit.getEntityJson());

        // Проверяем, что сообщение было отправлено в Kafka
        verify(kafkaTemplate, times(1)).send(eq("audit.logs"), anyString());
    }

    @Test
    public void testGetAllAuditLogs() {
        // Добавляем несколько логов
        auditService.logAuditEvent("User", "Create", "admin", "admin", "{\"name\":\"John\"}", "{\"name\":\"Doe\"}");
        auditService.logAuditEvent("User", "Update", "admin", "admin", "{\"name\":\"John Updated\"}", "{\"name\":\"Doe Updated\"}");

        // Получаем все логи
        List<Audit> auditLogs = auditService.getAllAuditLogs();

        // Проверяем, что количество логов верное
        assertEquals(2, auditLogs.size());
    }
}
