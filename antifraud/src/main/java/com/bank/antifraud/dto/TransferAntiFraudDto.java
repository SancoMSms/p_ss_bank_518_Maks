package com.bank.antifraud.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransferAntiFraudDto {

    private Long transferId;

    private BigDecimal amount;

    private String entityType;

    private Boolean isBlocked;

    private Boolean isSuspicious;
}
