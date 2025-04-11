package com.bank.antifraud.services;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.dto.AbstractSuspiciousTransferDto;
import com.bank.antifraud.entities.Audit;
import com.bank.antifraud.mappers.AuditMapper;
import com.bank.antifraud.repositories.AuditRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private static final String CREATE = "CREATE";
    private static final String UPDATE = "UPDATE";
    private static final Logger LOGGER = LoggerFactory.getLogger(AuditServiceImpl.class);
    private final AuditRepository auditRepository;
    private final AuditMapper auditMapper;
    private final ObjectMapper objectMapper;


    @Override
    public AuditDto getDtoById(Long id) {
        return auditMapper.toDto(auditRepository.getById(id));
    }

    @Override
    @Transactional
    public void logAudit(AbstractSuspiciousTransferDto suspiciousTransferDto,
                         String operationType) {
        if (!CREATE.equals(operationType.toUpperCase()) &&
                !UPDATE.equals(operationType.toUpperCase())) {
            throw new IllegalArgumentException("Invalid operation type: " + operationType);
        }
        final AuditDto auditDto = new AuditDto();
        auditDto.setOperationType(operationType);
        auditDto.setTransferId(suspiciousTransferDto.getTransferId());
        auditDto.setEntityType(suspiciousTransferDto.getEntityType());
        if (CREATE.equals(operationType)) {
            auditDto.setCreatedBy("SYSTEM"); //TODO брать из пришедшего с кафки аудита
            auditDto.setCreatedAt(Timestamp.valueOf(LocalDateTime.now())); //TODO брать из пришедшего с кафки аудита
            setJson(auditDto, CREATE, suspiciousTransferDto);
        } else if (UPDATE.equals(operationType)) {
            final List<Audit> previousAudits =
                    auditRepository.findPreviousByTransferId(suspiciousTransferDto.getEntityType(),
                            suspiciousTransferDto.getTransferId());
            final Audit oldAuditDto = previousAudits.isEmpty() ? null : previousAudits.get(0);
            auditDto.setEntityType(oldAuditDto.getEntityType());
            auditDto.setCreatedBy(oldAuditDto.getCreatedBy());
            auditDto.setCreatedAt(oldAuditDto.getCreatedAt());
            auditDto.setEntityJson(oldAuditDto.getEntityJson());
            auditDto.setModifiedAt(Timestamp.valueOf(LocalDateTime.now()));
            auditDto.setModifiedBy("anti_fraud");
            setJson(auditDto, UPDATE, suspiciousTransferDto);
        }
        final Audit audit = auditMapper.toEntity(auditDto);
        auditRepository.save(audit);
        final AuditDto auditDtoFromRep = auditMapper.toDto(auditRepository.getById(audit.getId()));
        LOGGER.info("Audit event saved successfully: {}", auditDtoFromRep);
    }

    private void setJson(AuditDto auditDto,
                         String operationType,
                         AbstractSuspiciousTransferDto suspiciousTransferDto) {
        try {
            if (operationType.equals(CREATE)) {
                auditDto.setEntityJson(objectMapper.writeValueAsString(suspiciousTransferDto));
            } else if (operationType.equals(UPDATE)) {
                auditDto.setEntityJson(objectMapper.writeValueAsString(suspiciousTransferDto));
            }
        } catch (JsonProcessingException e) {
            LOGGER.error("Error while serializing object to JSON: {}", e.getMessage());
            throw new RuntimeException("Failed to serialize object to JSON", e);
        }
    }
}
