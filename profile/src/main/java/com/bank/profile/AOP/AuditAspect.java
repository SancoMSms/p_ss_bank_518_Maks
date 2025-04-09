package com.bank.profile.AOP;

import com.bank.profile.Entities.Profile;
import com.bank.profile.Enums.AuditEntityType;
import com.bank.profile.Enums.AuditOperationType;
import com.bank.profile.Services.AuditService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditService auditService;
    private final ObjectMapper objectMapper;

    @AfterReturning(value = "execution(* com.bank.profile.Services.ProfileServiceImpl.create(..))", returning = "profile")
    public void logProfileCreation(JoinPoint joinPoint, Profile profile) {
        logAudit(AuditEntityType.PROFILE, AuditOperationType.CREATE, profile, null);
    }

    @AfterReturning(value = "execution(* com.bank.profile.Services.ProfileServiceImpl.update(..))", returning = "updatedProfile")
    public void logProfileUpdate(JoinPoint joinPoint, Profile updatedProfile) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof Profile) {
            Profile oldProfile = (Profile) args[0];
            logAudit(AuditEntityType.PROFILE, AuditOperationType.UPDATE, updatedProfile, oldProfile);
        }
    }

    private void logAudit(AuditEntityType entityType, AuditOperationType operationType, Object newEntity, Object oldEntity) {
        String newEntityJson = serialize(newEntity);
        String oldEntityJson = oldEntity != null ? serialize(oldEntity) : null;
        String username = getCurrentUsername();

        auditService.logAuditEvent(
                entityType.name(),
                operationType.name(),
                username,
                username,
                newEntityJson,
                oldEntityJson
        );
    }

    private String serialize(Object entity) {
        try {
            return objectMapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка при сериализации объекта аудита", e);
        }
    }

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null && auth.isAuthenticated()) ? auth.getName() : "UNKNOWN";
    }
}
