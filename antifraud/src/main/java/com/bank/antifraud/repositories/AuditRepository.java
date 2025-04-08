package com.bank.antifraud.repositories;

import com.bank.antifraud.entities.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {
    Audit getById(Long id);
}