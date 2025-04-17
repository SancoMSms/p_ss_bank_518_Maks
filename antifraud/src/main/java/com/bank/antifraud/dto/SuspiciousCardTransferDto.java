package com.bank.antifraud.dto;

import com.bank.antifraud.enums.TransferType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@RequiredArgsConstructor
@Validated
public class SuspiciousCardTransferDto extends AbstractSuspiciousTransferDto {

    public void setCardTransferId(Long cardTransferId) {
        this.setTransferId(cardTransferId);
    }

    public Long getCardTransferId() {
        return this.getTransferId();
    }

    @Override
    public TransferType getEntityType() {
        return TransferType.CARD;
    }
}
