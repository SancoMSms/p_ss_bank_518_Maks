package com.bank.antifraud.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SuspiciousPhoneTransferDto extends AbstractSuspiciousTransferDto {

    public void setPhoneTransferId(Long phoneTransferId) {
        this.setTransferId(phoneTransferId);
    }

    public Long getPhoneTransferId() {
        return this.getTransferId();
    }

    @Override
    public String getEntityType() {
        return "PHONE";
    }
}
