package com.bank.profile.AOP;

import com.bank.profile.Entities.Profile;
import com.bank.profile.Services.AuditService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {
    private final AuditService auditService;
    private final ObjectMapper objectMapper;

    @AfterReturning(value = "execution(* com.bank.profile.Services.ProfileServiceImpl.create(..))", returning = "profile")
    public void logProfileCreation(JoinPoint joinPoint, Profile profile) {
        logAudit("Profile", "CREATE", profile, null);
    }

    @AfterReturning(value = "execution(* com.bank.profile.Services.ProfileServiceImpl.update(..))", returning = "updatedProfile")
    public void logProfileUpdate(JoinPoint joinPoint, Profile updatedProfile) {
        Profile oldProfile = (Profile) joinPoint.getArgs()[0]; // Получаем старое состояние из аргументов метода
        logAudit("Profile", "UPDATE", updatedProfile, oldProfile);
    }

    private void logAudit(String entityType, String operationType, Profile newProfile, Profile oldProfile) {
        try {
            String newEntityJson = objectMapper.writeValueAsString(newProfile);
            String oldEntityJson = oldProfile != null ? objectMapper.writeValueAsString(oldProfile) : null;
            auditService.logAuditEvent(
                    entityType,
                    operationType,
                    "SYSTEM",  // Можно заменить на SecurityContextHolder.getContext().getAuthentication().getName()
                    "SYSTEM",
                    newEntityJson,
                    oldEntityJson
            );
        } catch (Exception e) {
            e.printStackTrace(); // Логируем ошибку сериализации
        }
    }
}
