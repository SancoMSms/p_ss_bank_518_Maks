package com.bank.antifraud.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;

@Data
public abstract class SuspiciousTransferDto {

    @Setter(AccessLevel.NONE)
    private Long id;

    private Boolean isBlocked;

    private Boolean isSuspicious;

    private String blockedReason;

    private String suspiciousReason;

    private Long transferId;

    private BigDecimal amount;

    public abstract String getEntityType();

    public void setTransferId(Long id) {
        this.id = id;
        this.transferId = id;
    }
}
