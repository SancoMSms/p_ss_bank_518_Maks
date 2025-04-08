package com.bank.antifraud.services;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.dto.SuspiciousTransferDto;
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

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private static final Logger logger = LoggerFactory.getLogger(AuditServiceImpl.class);
    private final AuditRepository auditRepository;
    private final AuditMapper auditMapper;
    private final ObjectMapper objectMapper;

    @Override
    public AuditDto getDtoById(Long id) {
        return auditMapper.toDto(auditRepository.getById(id));
    }

    @Override
    @Transactional
    public void logAudit(SuspiciousTransferDto suspiciousTransferDto,
                         String operation_type) {

        AuditDto auditDto = new AuditDto();
        auditDto.setOperation_type(operation_type);

        if (!"CREATE".equals(operation_type) && !"UPDATE".equals(operation_type)) {
            throw new IllegalArgumentException("Invalid operation type: " + operation_type);
        }
        auditDto.setEntity_type(suspiciousTransferDto.getEntityType());
        if ("CREATE".equals(operation_type)) {
            auditDto.setCreated_by("SYSTEM"); //TODO брать из пришедшего с кафки аудита
            auditDto.setCreated_at(Timestamp.valueOf(LocalDateTime.now())); //TODO брать из пришедшего с кафки аудита
            try {
                auditDto.setEntity_json(objectMapper.writeValueAsString(suspiciousTransferDto));
            } catch (JsonProcessingException e) {
                logger.error("Error while serializing object to JSON: {}", e.getMessage());
                throw new RuntimeException("Failed to serialize object to JSON", e);
            }
        } else if ("UPDATE".equals(operation_type)) {
            AuditDto oldAuditDto = getDtoById(suspiciousTransferDto.getTransferId());
            auditDto.setEntity_type(oldAuditDto.getEntity_type());
            auditDto.setCreated_by(oldAuditDto.getCreated_by());
            auditDto.setCreated_at(oldAuditDto.getCreated_at());
            auditDto.setEntity_json(oldAuditDto.getEntity_json());
            auditDto.setModified_at(Timestamp.valueOf(LocalDateTime.now()));
            auditDto.setModified_by("anti_fraud");
            try {
                auditDto.setNew_entity_json(objectMapper.writeValueAsString(suspiciousTransferDto));
            } catch (JsonProcessingException e) {
                logger.error("Error while serializing object to JSON: {}", e.getMessage());
                throw new RuntimeException("Failed to serialize object to JSON", e);
            }
        }
        Audit audit = auditMapper.toEntity(auditDto);
        auditRepository.save(audit);
        AuditDto auditDtoFromRep = auditMapper.toDto(auditRepository.getById(audit.getId()));
        logger.info("Audit event saved successfully: {}", auditDtoFromRep);
    }
}