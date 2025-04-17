package com.bank.antifraud.dto;

import com.bank.antifraud.enums.TransferType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractSuspiciousTransferDto {

    private Long id;

    @NotNull(message = "field isBlocked cannot be null")
    private Boolean isBlocked;

    @NotNull(message = "field isSuspicious cannot be null")
    private Boolean isSuspicious;

    private String blockedReason;

    @NotNull(message = "field suspiciousReason cannot be null")
    private String suspiciousReason;

    @NotNull(message = "field transferId cannot be null")
    private Long transferId;

    @Positive
    private BigDecimal amount;

    public abstract TransferType getEntityType();

    public void setTransferId(Long inputId) {
        this.id = inputId;
        this.transferId = inputId;
    }
}
