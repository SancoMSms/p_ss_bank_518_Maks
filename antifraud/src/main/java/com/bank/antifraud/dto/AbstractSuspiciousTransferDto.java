package com.bank.antifraud.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;

@Data
public abstract class AbstractSuspiciousTransferDto {

    @Setter(AccessLevel.NONE)
    private Long id;

    @NotNull
    private Boolean isBlocked;

    @NotNull
    private Boolean isSuspicious;

    private String blockedReason;

    @NotNull
    private String suspiciousReason;

    @NotNull

    private Long transferId;

    @Positive
    private BigDecimal amount;

    public abstract String getEntityType();

    public void setTransferId(Long inputId) {
        this.id = inputId;
        this.transferId = inputId;
    }
}
