package com.bank.antifraud.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "suspicious_phone_transfers")
public class SuspiciousPhoneTransfer extends AbstractSuspiciousTransfer {

    private Long phoneTransferId;

    @Override
    public Long getTransferId() {
        return phoneTransferId;
    }

    @Override
    public void setTransferId(Long transferId) {
        this.phoneTransferId = transferId;
    }
}
