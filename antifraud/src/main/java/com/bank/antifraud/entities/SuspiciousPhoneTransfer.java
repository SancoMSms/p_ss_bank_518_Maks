package com.bank.antifraud.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@RequiredArgsConstructor
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
