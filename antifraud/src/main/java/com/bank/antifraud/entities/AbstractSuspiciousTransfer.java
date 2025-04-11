package com.bank.antifraud.entities;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class AbstractSuspiciousTransfer {

    @Id
    private Long id;

    private Boolean isBlocked;

    private Boolean isSuspicious;

    private String blockedReason;

    private String suspiciousReason;

    public abstract Long getTransferId();

    public abstract void setTransferId(Long transferId);

    public void syncIdWithTransferId() {
        this.id = getTransferId();
    }

    @Override
    public String toString() {
        return String.format(
                "%s{id=%d, " +
                        "transferId=%d, " +
                        "is_blocked=%s, " +
                        "is_suspicious=%s, " +
                        "blocked_reason='%s', " +
                        "suspicious_reason='%s'}",
                this.getClass().getSimpleName(),
                getId(),
                getTransferId(),
                isBlocked,
                isSuspicious,
                blockedReason,
                suspiciousReason
        );
    }
}
