package com.bank.antifraud.repositories;

import com.bank.antifraud.entities.AbstractSuspiciousTransfer;
import com.bank.antifraud.enums.TransferType;
import com.bank.antifraud.exception.ValidationException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import java.util.EnumMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SuspiciousTransferRepositoryManager {

    private final SuspiciousCardTransferRepository cardRepo;
    private final SuspiciousPhoneTransferRepository phoneRepo;
    private final SuspiciousAccountTransferRepository accountRepo;

    private final Map<TransferType, JpaRepository<? extends AbstractSuspiciousTransfer, Long>>
            repositoryMap = new EnumMap<>(TransferType.class);

    @PostConstruct
    public void init() {
        repositoryMap.put(TransferType.CARD, cardRepo);
        repositoryMap.put(TransferType.PHONE, phoneRepo);
        repositoryMap.put(TransferType.ACCOUNT, accountRepo);
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractSuspiciousTransfer> JpaRepository<T, Long> getRepository(TransferType transferType) {
        final JpaRepository<? extends AbstractSuspiciousTransfer, Long> repository =
                repositoryMap.get(transferType);
        if (repository == null) {
            throw new ValidationException("Unsupported transfer type: " + transferType);
        }
        return (JpaRepository<T, Long>) repository;
    }
}
