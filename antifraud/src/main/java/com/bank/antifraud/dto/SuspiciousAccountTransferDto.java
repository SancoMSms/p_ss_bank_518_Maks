package com.bank.antifraud.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SuspiciousAccountTransferDto extends AbstractSuspiciousTransferDto {

    public void setAccountTransferId(Long accountTransferId) {
        this.setTransferId(accountTransferId);
    }

    public Long getAccountTransferId() {
        return this.getTransferId();
    }

    @Override
    public String getEntityType() {
        return "ACCOUNT";
    }
}
