package com.bank.antifraud.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "suspicious_card_transfers")
public class SuspiciousCardTransfer extends SuspiciousTransfer {

    @Column(nullable = false, unique = true)
    private Long card_transfer_id;

}


