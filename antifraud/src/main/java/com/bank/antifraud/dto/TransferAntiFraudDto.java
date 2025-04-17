package com.bank.antifraud.dto;

import com.bank.antifraud.enums.TransferType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferAntiFraudDto {

    @NotNull
    private Long transferId;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private TransferType entityType;

    private Boolean isBlocked;

    private Boolean isSuspicious;
}
