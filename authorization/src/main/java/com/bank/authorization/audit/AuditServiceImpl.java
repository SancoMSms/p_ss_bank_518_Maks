package com.bank.authorization.audit;

import com.bank.authorization.DTO.AuditDto;
import com.bank.authorization.Entities.Audit;
import com.bank.authorization.Services.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;

    @Autowired
    public AuditServiceImpl(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Override
    public void audit(AuditDto auditDTO) {
        Audit audit = new Audit();
        audit.setEntityType(auditDTO.getEntityType());
        audit.setOperationType(auditDTO.getOperationType());
        audit.setCreatedBy(auditDTO.getCreatedBy());
        audit.setModifiedBy(auditDTO.getModifiedBy());
        audit.setCreatedAt(auditDTO.getCreatedAt());
        audit.setModifiedAt(auditDTO.getModifiedAt());
        audit.setNewEntityJson(auditDTO.getNewEntityJson());
        audit.setEntityJson(auditDTO.getEntityJson());

        this.auditRepository.save(audit);

    }
}

