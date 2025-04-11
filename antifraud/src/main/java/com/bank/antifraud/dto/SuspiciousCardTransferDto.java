package com.bank.antifraud.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SuspiciousCardTransferDto extends AbstractSuspiciousTransferDto {

    public void setCardTransferId(Long cardTransferId) {
        this.setTransferId(cardTransferId);
    }

    public Long getCardTransferId() {
        return this.getTransferId();
    }

    @Override
    public String getEntityType() {
        return "CARD";
    }
}
