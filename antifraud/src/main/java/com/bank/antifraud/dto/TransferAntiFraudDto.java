package com.bank.antifraud.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransferAntiFraudDto {

    @NotNull
    private Long transferId;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private String entityType;

    @NotNull
    private Boolean isBlocked;

    @NotNull
    private Boolean isSuspicious;
}
