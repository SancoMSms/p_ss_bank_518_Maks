package com.bank.publicinfo.aop;

import com.bank.publicinfo.entity.Audit;
import com.bank.publicinfo.enums.OperationType;
import com.bank.publicinfo.repository.AuditRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditRepository auditRepository;
    private final ObjectMapper objectMapper;

    @AfterReturning(pointcut =
            "execution(* com.bank.publicinfo.service.*.add*(..))", returning = "returnedObject")
    public void afterCreateMethod(Object returnedObject) {
        log.info("Audit for CREATE operation started.");
        auditOperation(returnedObject, OperationType.CREATE);
    }

    @AfterReturning(pointcut =
            "execution(* com.bank.publicinfo.service.*.update*(..))",
            returning = "returnedObject")
    public void afterUpdateMethod(Object returnedObject) {
        log.info("Audit for UPDATE operation started.");
        auditOperation(returnedObject, OperationType.UPDATE);
    }


    private void auditOperation(Object returnedObject, OperationType operationType) {
        try {
            if (returnedObject == null) {
                log.warn("Finish result is null for {} operation; skipping audit.", operationType);
                return;
            }
            log.debug("Starting audit for {} operation.", operationType);
            final String entityJson = objectMapper.writeValueAsString(returnedObject);
            log.debug("Serialized entity: {}", entityJson);
            final String entityType = returnedObject.getClass().getSimpleName();
            log.debug("Entity type: {}", entityType);
            final String createdBy = "system";
            log.debug("Created by: {}", createdBy);

            Audit auditEntity = new Audit();
            auditEntity.setEntityType(entityType);
            auditEntity.setOperationType(operationType);
            auditEntity.setCreatedBy(createdBy);
            auditEntity.setCreatedAt(LocalDateTime.now());
            auditEntity.setEntityJson(entityJson);
            auditRepository.save(auditEntity);
            log.info("Audit recorded for {} operation on entity: {}", operationType, entityType);
        } catch (Exception e) {
            log.error("Error during {} audit operation: {}", operationType, e.getMessage(), e);
        }
    }
}
