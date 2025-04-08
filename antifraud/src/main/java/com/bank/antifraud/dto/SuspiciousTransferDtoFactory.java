package com.bank.antifraud.dto;

import com.bank.antifraud.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SuspiciousTransferDtoFactory {

    public SuspiciousTransferDto createSuspiciousTransferDto(String entityType) {
        if (entityType == null) {
            throw new ValidationException("entityType is null!! in createSuspiciousTransferDto");
        }

        switch (entityType.toUpperCase()) {
            case "CARD":
                return new SuspiciousCardTransferDto();
            case "PHONE":
                return new SuspiciousPhoneTransferDto();
            case "ACCOUNT":
                return new SuspiciousAccountTransferDto();
            default:
                throw new ValidationException("Unsupported transfer type: " + entityType);
        }
    }
}
