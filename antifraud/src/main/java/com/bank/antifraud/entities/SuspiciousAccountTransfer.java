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
