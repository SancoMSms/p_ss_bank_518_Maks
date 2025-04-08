package com.bank.antifraud.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SuspiciousAccountTransferDto extends SuspiciousTransferDto{

    public void setAccount_transfer_id(Long account_transfer_id) {
        this.setTransferId(account_transfer_id);
    }

    public Long getAccount_transfer_id() {
        return this.getTransferId();
    }

    @Override
    public String getEntityType() {
        return "ACCOUNT";
    }
}
