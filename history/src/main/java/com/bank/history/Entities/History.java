package com.bank.history.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table
@Data
@RequiredArgsConstructor
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int transfer_audit_id;
    private int profile_audit_id;
    private int account_audit_id;
    private int anti_fraud_audit_id;
    private int public_bank_info_audit_id;
    private int authorization_audit_id;
}
