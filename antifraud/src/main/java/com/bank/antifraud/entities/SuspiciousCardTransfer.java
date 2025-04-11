package com.bank.antifraud.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
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
