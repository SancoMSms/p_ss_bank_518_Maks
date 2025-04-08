package com.bank.antifraud.repositories;

import com.bank.antifraud.entities.SuspiciousCardTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuspiciousCardTransferRepository extends JpaRepository<SuspiciousCardTransfer, Long> {
    Boolean existsByCard_transfer_id(Long transfer_id);
}
