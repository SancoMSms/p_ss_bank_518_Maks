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
public class SuspiciousAccountTransferDto extends AbstractSuspiciousTransferDto {

    public void setAccountTransferId(Long accountTransferId) {
        this.setTransferId(accountTransferId);
    }

    public Long getAccountTransferId() {
        return this.getTransferId();
    }

    @Override
    public TransferType getEntityType() {
        return TransferType.ACCOUNT;
    }
}
