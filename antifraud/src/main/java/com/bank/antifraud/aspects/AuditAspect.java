package com.bank.antifraud.aspects;

import com.bank.antifraud.dto.AbstractSuspiciousTransferDto;
import com.bank.antifraud.services.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditAspect {

    private final AuditService auditService;

    @Pointcut("execution(* com.bank.antifraud.services.SuspiciousTransferService.create*(..))")
    public void createOperation() { }

    @AfterReturning(pointcut = "createOperation()", returning = "suspiciousTransferDto")
    public void logCreate(AbstractSuspiciousTransferDto suspiciousTransferDto) {
        log.info("Logging CREATE operation for suspiciousTransferDto: {}", suspiciousTransferDto);
        auditService.logAudit(suspiciousTransferDto);
    }
}
