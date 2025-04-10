package com.bank.antifraud.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "suspicious_account_transfers")
public class SuspiciousAccountTransfer extends SuspiciousTransfer {

    @Column(nullable = false, unique = true)
    private Long account_transfer_id;

    @Override
    public Long getTransferId() {
        return account_transfer_id;
    }

    @Override
    public void setTransferId(Long transferId) {
        this.account_transfer_id = transferId;
    }
}
