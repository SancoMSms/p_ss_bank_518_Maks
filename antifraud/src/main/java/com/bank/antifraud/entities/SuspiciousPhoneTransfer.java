package com.bank.antifraud.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "suspicious_phone_transfers")
public class SuspiciousPhoneTransfer extends SuspiciousTransfer {

    @Column(nullable = false, unique = true)
    private Long phone_transfer_id;

    public void setPhone_transfer_id(Long id) {
        this.phone_transfer_id = id;

    }

}
