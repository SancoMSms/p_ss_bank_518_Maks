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
}
