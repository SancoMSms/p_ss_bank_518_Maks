package com.bank.antifraud.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "suspicious_account_transfers")
public class SuspiciousAccountTransfer extends AbstractSuspiciousTransfer {

    private Long accountTransferId;

    @Override
    public Long getTransferId() {
        return accountTransferId;
    }

    @Override
    public void setTransferId(Long transferId) {
        this.accountTransferId = transferId;
    }
}
