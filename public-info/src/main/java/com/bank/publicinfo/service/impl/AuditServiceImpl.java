package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.AuditDto;
import com.bank.publicinfo.entity.Audit;
import com.bank.publicinfo.mapper.AuditMapper;
import com.bank.publicinfo.repository.AuditRepository;
import com.bank.publicinfo.service.AuditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;
    private final AuditMapper auditMapper;

    @Override
    @Transactional
    public AuditDto addAudit(AuditDto auditDto) {
        validateAuditDto(auditDto);

        try {
            Audit savedAudit = auditRepository.save(auditMapper.toEntity(auditDto));
            return auditMapper.toDto(savedAudit);
        } catch (Exception e) {
            log.error("Failed to save Audit", e);
            throw new RuntimeException("Unable to save audit", e);
        }
    }

    @Override
    public List<AuditDto> getAllAudits() {
        return auditMapper.toDtoList(auditRepository.findAll());
    }

    @Override
    public AuditDto getAuditById(Long id) {
        Audit audit = auditRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Audit not found with id: " + id));
        return auditMapper.toDto(audit);
    }

    @Override
    @Transactional
    public AuditDto updateAudit(AuditDto auditDto) {
        validateAuditDto(auditDto);

        if (auditDto.getId() == null) {
            throw new IllegalArgumentException("Audit ID must not be null when updating.");
        }

        auditRepository.findById(auditDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Cannot update: Audit not found with id: " + auditDto.getId()));

        Audit updatedAudit = auditRepository.save(auditMapper.toEntity(auditDto));
        return auditMapper.toDto(updatedAudit);
    }

    private void validateAuditDto(AuditDto auditDto) {
        if (auditDto.getEntityType() == null ||
                auditDto.getOperationType() == null ||
                auditDto.getCreatedBy() == null ||
                auditDto.getEntityJson() == null) {
            throw new IllegalArgumentException("Audit fields cannot be null: entityType, operationType, createdBy, entityJson must be provided.");
        }
    }
}

