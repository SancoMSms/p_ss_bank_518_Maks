package com.bank.profile.Services;

import com.bank.profile.Entities.Audit;
import java.util.List;

public interface AuditService {
    void logAuditEvent(String entityType, String operationType, String createdBy, String modifiedBy, String newEntityJson, String entityJson);
    List<Audit> getAllAuditLogs();
}
