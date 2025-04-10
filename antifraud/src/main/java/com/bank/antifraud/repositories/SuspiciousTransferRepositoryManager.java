package com.bank.antifraud.repositories;

import com.bank.antifraud.entities.SuspiciousTransfer;
import com.bank.antifraud.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SuspiciousTransferRepositoryManager {

    private final SuspiciousCardTransferRepository cardRepo;
    private final SuspiciousPhoneTransferRepository phoneRepo;
    private final SuspiciousAccountTransferRepository accountRepo;

    public <T extends SuspiciousTransfer> JpaRepository<T, Long> getRepository(String entityType) {
        return switch (entityType.toUpperCase()) {
            case "CARD" -> (JpaRepository<T, Long>) cardRepo;
            case "PHONE" -> (JpaRepository<T, Long>) phoneRepo;
            case "ACCOUNT" -> (JpaRepository<T, Long>) accountRepo;
            default -> throw new ValidationException("Unsupported transfer type: " + entityType);
        };
    }
}
