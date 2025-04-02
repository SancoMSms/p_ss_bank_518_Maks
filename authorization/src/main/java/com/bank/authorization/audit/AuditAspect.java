package com.bank.authorization.audit;

import com.bank.authorization.DTO.AuditDto;
import com.bank.authorization.DTO.UserDto;
import com.bank.authorization.Entities.User;
import com.bank.authorization.Repositories.UserRepository;
import com.bank.authorization.Services.AuditService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
@Aspect
@Component
public class AuditAspect {

    private final AuditService auditService;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @Autowired
    public AuditAspect(AuditService auditService, AuthenticationService authenticationService, UserRepository userRepository) {
        this.auditService = auditService;
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }

    // Аудит создания пользователя (только если метод завершился успешно)
    @AfterReturning("execution(* com.bank.authorization.Services.UserServiceImpl.createUser(..))")
    public void logCreateAudit(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        UserDto userDto = (UserDto) args[0];  // Приводим к UserDto
        Long profileId = userDto.getProfileId();  // Извлекаем profileId из UserDto
        String entityType = joinPoint.getSignature().getDeclaringType().getSimpleName();// название класса
        String operationType = joinPoint.getSignature().getName();// название метода
        String createdBy = authenticationService.getCurrentUsername();// кто создал
        String modifiedBy = authenticationService.getCurrentUsername();// кто изменил
        OffsetDateTime createdAt = OffsetDateTime.now();  // время создания
        OffsetDateTime modifiedAt = OffsetDateTime.now();  // время изменения
        String newEntityJson = "";
        String entityJson = "";
        // Создаем объект AuditDto
        AuditDto auditDto = new AuditDto();
        auditDto.setEntityType(entityType);
        auditDto.setOperationType(operationType);
        auditDto.setCreatedBy(createdBy);
        auditDto.setModifiedBy(modifiedBy);  // Можно оставить одинаковым, если не отличается
        auditDto.setCreatedAt(createdAt);
        auditDto.setModifiedAt(modifiedAt);
        auditDto.setNewEntityJson(newEntityJson);
        auditDto.setEntityJson(entityJson);

        System.out.println(">>> Аудит: метод createUser успешно выполнен для profileId: " + profileId);

        try {
            auditService.audit(auditDto);
        } catch (Exception e) {
            System.out.println(">>> Ошибка в auditService.audit: " + e.getMessage());
        }
    }

    // Аудит обновления пользователя (только если метод завершился успешно)
    @AfterReturning("execution(* com.bank.authorization.Services.UserServiceImpl.updateUser(..))")
    public void logUpdateAudit(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        UserDto userDto = (UserDto) args[0];  // Приводим к UserDto
        Long profileId = userDto.getProfileId();  // Извлекаем profileId из UserDto
        String entityType = joinPoint.getSignature().getDeclaringType().getSimpleName();// название класса
        String operationType = joinPoint.getSignature().getName();// название метода
        String createdBy = authenticationService.getCurrentUsername();// кто создал
        String modifiedBy = authenticationService.getCurrentUsername();// кто изменил
        OffsetDateTime createdAt = OffsetDateTime.now();  // время создания
        OffsetDateTime modifiedAt = OffsetDateTime.now();  // время изменения
        String newEntityJson = "";
        String entityJson = "";

        // Создаем объект AuditDto
        AuditDto auditDto = new AuditDto();
        auditDto.setEntityType(entityType);
        auditDto.setOperationType(operationType);
        auditDto.setCreatedBy(createdBy);
        auditDto.setModifiedBy(modifiedBy);
        auditDto.setCreatedAt(createdAt);
        auditDto.setModifiedAt(modifiedAt);
        auditDto.setNewEntityJson(newEntityJson);
        auditDto.setEntityJson(entityJson);


        System.out.println(">>> Аудит: метод updateUser успешно выполнен для profileId: " + profileId);

        try {
            // Получаем старые данные пользователя (до обновления)
            User oldUser = userRepository.findByProfileId(profileId)
                    .orElseThrow(() -> new IllegalArgumentException("Пользователь с profileId " + profileId + " не найден"));

            // Получаем новые данные пользователя (после обновления)
            User newUser = userRepository.findByProfileId(profileId)
                    .orElseThrow(() -> new IllegalArgumentException("Пользователь с profileId " + profileId + " не найден"));

            // Сериализация старого состояния пользователя в JSON (entityJson)
            ObjectMapper objectMapper = new ObjectMapper();
            entityJson = objectMapper.writeValueAsString(oldUser); // Старое состояние

            // Сериализация нового состояния пользователя в JSON (newEntityJson)
            newEntityJson = objectMapper.writeValueAsString(newUser); // Новое состояние

            // Заполняем новые данные в AuditDto
            auditDto.setNewEntityJson(newEntityJson);
            auditDto.setEntityJson(entityJson);

            // Логируем обновление в таблице аудита
            auditService.audit(auditDto);
        } catch (Exception e) {
            System.out.println(">>> Ошибка в auditService.audit: " + e.getMessage());
        }
    }
}


