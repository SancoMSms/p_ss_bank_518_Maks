package com.bank.antifraud.dto;

import com.bank.antifraud.enums.TransferType;
import jakarta.validation.Valid;
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
public class SuspiciousPhoneTransferDto extends AbstractSuspiciousTransferDto {

    public void setPhoneTransferId(@Valid Long phoneTransferId) {
        this.setTransferId(phoneTransferId);
    }

    public Long getPhoneTransferId() {
        return this.getTransferId();
    }

    @Override
    public TransferType getEntityType() {
        return TransferType.PHONE;
    }
}
