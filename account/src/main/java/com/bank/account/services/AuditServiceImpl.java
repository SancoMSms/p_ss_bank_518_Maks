package com.bank.account.services;

import com.bank.account.dto.AuditDto;
import com.bank.account.entities.Audit;
import com.bank.account.mappers.AuditMapper;
import com.bank.account.repositories.AuditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;
    private final AuditMapper auditMapper;

    @Override
    public void saveAudit(AuditDto auditDto) {
        if (auditDto == null || auditDto.getEntityType() == null || auditDto.getOperationType() == null) {
            throw new IllegalArgumentException("AuditDto, entityType, и operationType не могут быть null");
        }
        try {
            final Audit audit = auditMapper.toEntity(auditDto);
            auditRepository.save(audit);
            log.info("Запись аудита сохранена. Тип сущности: {}, Тип операции: {}",
                    audit.getEntityType(), audit.getOperationType());
        } catch (Exception e) {
            log.info("Не удалось сохранить запись аудита. Тип сущности: {}, Тип операции: {}",
                    auditDto.getEntityType(), e.getMessage());
            throw new RuntimeException("Не удалось сохранить запись аудита", e);
        }
    }
}
