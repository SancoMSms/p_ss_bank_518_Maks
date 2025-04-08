package com.bank.antifraud.aspects;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.dto.SuspiciousTransferDto;
import com.bank.antifraud.mappers.AuditMapper;
import com.bank.antifraud.mappers.SuspiciousTransferMapper;
import com.bank.antifraud.repositories.AuditRepository;
import com.bank.antifraud.services.AuditService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private static final Logger logger = LoggerFactory.getLogger(AuditAspect.class);
    private final AuditService auditService;

    @Pointcut("execution(* com.bank.antifraud.services.SuspiciousTransferServiceImpl.create*(..))")
    public void createOperation() {}

    @Pointcut("execution(* com.bank.antifraud.services.SuspiciousTransferServiceImpl.update*(..))")
    public void updateOperation() {}

    @AfterReturning(pointcut = "createOperation()", returning = "suspiciousTransferDto")
    public void logCreate(SuspiciousTransferDto suspiciousTransferDto) {
        logger.info("Logging CREATE operation for suspiciousTransferDto: {}", suspiciousTransferDto);
        auditService.logAudit(suspiciousTransferDto, "CREATE");
    }

    @AfterReturning(pointcut = "updateOperation()", returning = "suspiciousTransferDto")
    public void logUpdate(SuspiciousTransferDto suspiciousTransferDto) {
        logger.info("Logging UPDATE operation for suspiciousTransferDto: {}", suspiciousTransferDto);
        auditService.logAudit(suspiciousTransferDto, "UPDATE");
    }
}
