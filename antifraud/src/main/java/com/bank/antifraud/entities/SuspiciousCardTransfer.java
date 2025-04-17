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
@Table(name = "suspicious_card_transfers")
public class SuspiciousCardTransfer extends AbstractSuspiciousTransfer {

    private Long cardTransferId;

    @Override
    public Long getTransferId() {
        return cardTransferId;
    }

    @Override
    public void setTransferId(Long transferId) {
        this.cardTransferId = transferId;
    }
}
