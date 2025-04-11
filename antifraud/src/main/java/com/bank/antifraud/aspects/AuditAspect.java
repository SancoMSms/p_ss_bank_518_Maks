package com.bank.antifraud.aspects;

import com.bank.antifraud.dto.AbstractSuspiciousTransferDto;
import com.bank.antifraud.services.AuditService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditAspect.class);
    private final AuditService auditService;

    @Pointcut("execution(* com.bank.antifraud.services.SuspiciousTransferServiceImpl.create*(..))")
    public void createOperation() { }

    @Pointcut("execution(* com.bank.antifraud.services.SuspiciousTransferServiceImpl.update*(..))")
    public void updateOperation() { }

    @AfterReturning(pointcut = "createOperation()", returning = "suspiciousTransferDto")
    public void logCreate(AbstractSuspiciousTransferDto suspiciousTransferDto) {
        LOGGER.info("Logging CREATE operation for suspiciousTransferDto: {}", suspiciousTransferDto);
        auditService.logAudit(suspiciousTransferDto, "CREATE");
    }

    @AfterReturning(pointcut = "updateOperation()", returning = "suspiciousTransferDto")
    public void logUpdate(AbstractSuspiciousTransferDto suspiciousTransferDto) {
        LOGGER.info("Logging UPDATE operation for suspiciousTransferDto: {}", suspiciousTransferDto);
        auditService.logAudit(suspiciousTransferDto, "UPDATE");
    }
}
