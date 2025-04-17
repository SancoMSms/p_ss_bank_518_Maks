package com.bank.antifraud.services;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.dto.AbstractSuspiciousTransferDto;
import com.bank.antifraud.entities.Audit;
import com.bank.antifraud.enums.OperationType;
import com.bank.antifraud.kafka.producer.AuditProducer;
import com.bank.antifraud.mappers.AuditMapper;
import com.bank.antifraud.repositories.AuditRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;
    private final AuditMapper auditMapper;
    private final ObjectMapper objectMapper;
    private final AuditProducer auditProducer;

    @Override
    public AuditDto getDtoById(Long id) {
        return auditMapper.toDto(auditRepository.getById(id));
    }

    @Override
    @Transactional
    public void logAudit(@Valid AbstractSuspiciousTransferDto dto) {
        final OperationType operationType = getOperationType(dto);
        final AuditDto auditDto = new AuditDto();
        auditDto.setEntityType(dto.getEntityType());
        auditDto.setOperationType(operationType.name());
        auditDto.setTransferId(dto.getTransferId());
        auditDto.setCreatedBy("SYSTEM"); //TODO брать из пришедшего с кафки аудита
        auditDto.setModifiedBy("antifraud");
        auditDto.setCreatedAt(Timestamp.valueOf("2001-01-01 01:01:01")); //TODO брать из пришедшего с кафки аудита
        auditDto.setModifiedAt(Timestamp.valueOf(LocalDateTime.now()));
        setJson(auditDto, dto);
        final Audit savedAudit = auditRepository.save(auditMapper.toEntity(auditDto));
        final AuditDto auditDtoFromRep = auditMapper.toDto(savedAudit);
        log.info("Audit event saved successfully: {}", auditDtoFromRep);
        auditProducer.sendAuditEvent(auditDtoFromRep);
        log.info("Audit event sent to Kafka");
    }

    private OperationType getOperationType(AbstractSuspiciousTransferDto dto) {
        if (dto.getIsBlocked()) {
            return OperationType.BLOCK;
        } else {
            return OperationType.APPROVE;
        }
    }

    private void setJson(AuditDto auditDto,
                         AbstractSuspiciousTransferDto suspiciousTransferDto) {
        try {
            auditDto.setEntityJson("TEMP"); //TODO брать из пришедшего с кафки аудита
            auditDto.setNewEntityJson(objectMapper.writeValueAsString(suspiciousTransferDto));
        } catch (JsonProcessingException e) {
            log.error("Error while serializing object to JSON: {}", e.getMessage());
            throw new RuntimeException("Failed to serialize object to JSON", e);
        }
    }
}
