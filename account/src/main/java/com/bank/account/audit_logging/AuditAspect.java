package com.bank.account.audit_logging;

import com.bank.account.dto.AuditDto;
import com.bank.account.kafka.AuditProducer;
import com.bank.account.operation_type.OperationType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditAspect.class);

    private static final String CREATE_BY_SYSTEM = "System";

    private final AuditProducer auditProducer;
    private final ObjectMapper objectMapper;

    @Pointcut("execution(* com.bank.account.services.AccountService.createAccount(..))")
    public void createAccountOperation() {
    }

    @Pointcut("execution(* com.bank.account.services.AccountService.updateAccount(..))")
    public void updateAccountOperation() {
    }

    @AfterReturning(pointcut = "createAccountOperation()", returning = "result")
    public void logCreateAudit(Object result) {
        sendAudit(result, OperationType.CREATE);
    }

    @AfterReturning(pointcut = "updateAccountOperation()", returning = "result")
    public void logUpdateAudit(Object result) {
        sendAudit(result, OperationType.UPDATE);
    }

    private void sendAudit(Object result, OperationType operationType) {
        if (result == null) {
            LOGGER.warn("Логирование аудита пропущено: результат равен null для типа операции {}", operationType);
            return;
        }

        try {
            final String json = objectMapper.writeValueAsString(result);
            final AuditDto auditDto = AuditDto.builder()
                    .entityType("Account")
                    .operationType(operationType.name())
                    .createdBy(CREATE_BY_SYSTEM)
                    .createdAt(LocalDateTime.now())
                    .newEntityJson(json)
                    .build();

            auditProducer.sendAuditEvent(auditDto);
            LOGGER.info("Аудит успешно отправлен для типа операции {}", operationType);
        } catch (Exception e) {
            LOGGER.error("Не удалось отправить сообщение аудита для типа операции {}", operationType, e);
            throw new RuntimeException("Ошибка логирования аудита", e);
        }
    }
}
