package com.bank.antifraud.repositories;

import com.bank.antifraud.entities.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {
    Audit getById(Long id);

    @Query("SELECT a FROM Audit a WHERE a.entity_type = :entityType AND a.entity_json LIKE CONCAT('%\"transferId\":', :transferId, '%') ORDER BY a.created_at DESC")
    List<Audit> findPreviousByTransferId(@Param("entityType") String entityType, @Param("transferId") Long transferId);

}
