package com.bank.antifraud.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "suspicious_phone_transfers")
public class SuspiciousPhoneTransfer extends SuspiciousTransfer {

    @Column(nullable = false, unique = true)
    private Long phone_transfer_id;

    @Override
    public Long getTransferId() {
        return phone_transfer_id;
    }

    @Override
    public void setTransferId(Long transferId) {
        this.phone_transfer_id = transferId;
    }
}
