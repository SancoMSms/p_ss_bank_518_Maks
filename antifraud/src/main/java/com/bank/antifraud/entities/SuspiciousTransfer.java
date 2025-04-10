package com.bank.antifraud.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@MappedSuperclass
@Data
public abstract class SuspiciousTransfer {

    @Id
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private Boolean is_blocked;

    @Column(nullable = false)
    private Boolean is_suspicious;

    private String blocked_reason;

    @Column(nullable = false)
    private String suspicious_reason;

    public abstract Long getTransferId();

    public abstract void setTransferId(Long transferId);

    //@PrePersist
    public void syncIdWithTransferId() {
        this.id = getTransferId();
    }

    @Override
    public String toString() {
        return String.format(
                "%s{id=%d, transferId=%d, is_blocked=%s, is_suspicious=%s, blocked_reason='%s', suspicious_reason='%s'}",
                this.getClass().getSimpleName(),
                getId(),
                getTransferId(),
                is_blocked,
                is_suspicious,
                blocked_reason,
                suspicious_reason
        );
    }
}
