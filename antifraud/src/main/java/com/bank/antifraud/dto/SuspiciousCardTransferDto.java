package com.bank.antifraud.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SuspiciousCardTransferDto extends SuspiciousTransferDto {



    public void setCard_transfer_id(Long card_transfer_id) {
        this.setTransferId(card_transfer_id);
    }

    public Long getCard_transfer_id() {
        return this.getTransferId();
    }

    @Override
    public String getEntityType() {
        return "CARD";
    }
}
