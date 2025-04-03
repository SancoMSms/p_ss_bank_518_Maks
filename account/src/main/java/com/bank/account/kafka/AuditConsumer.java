package com.bank.account.kafka;

import com.bank.account.dto.AuditDto;
import com.bank.account.services.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditConsumer {

    private final AuditService auditService;

    @KafkaListener(topics = "audit.log", groupId = "audit-group")
    public void consumeAuditEvent(AuditDto auditDto) {
        try {
            log.info("Получено событие аудита. Тип сущности: {}, Тип операции: {}",
                    auditDto.getEntityType(), auditDto.getOperationType());
            auditService.saveAudit(auditDto);
        } catch (Exception e) {
            log.info("Не удалось обработать событие аудита. Тип сущности: {}, Ошибка: {}",
                    auditDto.getEntityType(), e.getMessage());
            throw e;
        }
    }
}
