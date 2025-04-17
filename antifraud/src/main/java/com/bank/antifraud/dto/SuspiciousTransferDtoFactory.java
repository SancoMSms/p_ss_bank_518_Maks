package com.bank.antifraud.dto;

import com.bank.antifraud.enums.TransferType;
import com.bank.antifraud.exception.ValidationException;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class SuspiciousTransferDtoFactory {

    private final Map<TransferType, Supplier<AbstractSuspiciousTransferDto>>
            dtoSuppliers = new EnumMap<>(TransferType.class);

    @PostConstruct
    public void init() {
        dtoSuppliers.put(TransferType.CARD, SuspiciousCardTransferDto::new);
        dtoSuppliers.put(TransferType.PHONE, SuspiciousPhoneTransferDto::new);
        dtoSuppliers.put(TransferType.ACCOUNT, SuspiciousAccountTransferDto::new);
    }

    public AbstractSuspiciousTransferDto createSuspiciousTransferDto(@Valid TransferType transferType) {
        if (transferType == null) {
            throw new ValidationException("transferType must not be null");
        }
        final Supplier<AbstractSuspiciousTransferDto> supplier = dtoSuppliers.get(transferType);
        if (supplier == null) {
            throw new ValidationException("Unsupported transfer type: " + transferType);
        }
        return supplier.get();
    }
}
