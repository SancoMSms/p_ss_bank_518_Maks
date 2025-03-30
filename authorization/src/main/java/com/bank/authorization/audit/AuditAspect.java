package com.bank.authorization.AOP;

import com.bank.authorization.Services.AuditService;
import com.bank.authorization.util.AuthenticationService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
@Aspect
@Component
public class AuditAspect {

    private final AuditService auditService;
    private final AuthenticationService authenticationService;  // Для получения текущего пользователя

    @Autowired
    public AuditAspect(AuditService auditService, AuthenticationService authenticationService) {
        this.auditService = auditService;
        this.authenticationService = authenticationService;
    }

    // Перехватываем метод create
    @Before("execution(* com.bank.authorization.Services.UserServiceImpl.createUser(..))")
    public void logCreateAudit(JoinPoint joinPoint) {
        // Получаем профиль и дату изменения
        Object[] args = joinPoint.getArgs();
        Long profileId = (Long) args[0];  // Извлекаем profileId
        OffsetDateTime modifiedAt = OffsetDateTime.now();  // Время изменения
        String modifiedBy = authenticationService.getCurrentUsername();  // Текущий пользователь (кто изменил)

        // Логируем создание
        auditService.audit(modifiedAt, modifiedBy);  // Записываем в таблицу аудита
    }

    // Перехватываем метод update
    @Before("execution(* com.bank.authorization.Services.UserServiceImpl.updateUser(..))")
    public void logUpdateAudit(JoinPoint joinPoint) {
        // Получаем профиль и дату изменения
        Object[] args = joinPoint.getArgs();
        Long profileId = (Long) args[0];  // Извлекаем profileId
        OffsetDateTime modifiedAt = OffsetDateTime.now();  // Время изменения
        String modifiedBy = authenticationService.getCurrentUsername();  // Текущий пользователь (кто изменил)

        // Логируем обновление
        auditService.audit(modifiedAt, modifiedBy);  // Записываем в таблицу аудита
    }
}

