package com.bank.antifraud.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SuspiciousPhoneTransferDto extends SuspiciousTransferDto {

    public void setPhone_transfer_id(Long phone_transfer_id) {
        this.setTransferId(phone_transfer_id);
    }

    public Long getPhone_transfer_id() {
        return this.getTransferId();
    }

    @Override
    public String getEntityType() {
        return "PHONE";
    }
}
