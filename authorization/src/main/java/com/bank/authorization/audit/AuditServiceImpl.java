package com.bank.authorization.Services;

import com.bank.authorization.Entities.Audit;
import com.bank.authorization.Repositories.AuditRepository;
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
    public void audit(OffsetDateTime modifiedAt, String modifiedBy) {
        Audit audit = new Audit();
        audit.setModifiedAt(modifiedAt); // когда изменил
        audit.setModifiedBy(modifiedBy); // кто изменили
        auditRepository.save(audit);
    }
}
