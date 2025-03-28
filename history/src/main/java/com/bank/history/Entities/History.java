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
    private long id;

    @Column(name = "transfer_audit_id")
    private  long  transferAuditId;

    @Column(name = "profile_audit_id")
    private  long  profileAuditId;

    @Column(name = "account_audit_id")
    private  long  accountAuditId;

    @Column(name = "anti_fraud_audit_id")
    private  long  antiFraudAuditId;

    @Column(name = "public_bank_info_audit_id")
    private  long  publicBankInfoAuditId;

    @Column(name = "authorization_audit_id")
    private  long  authorizationAuditId;
}
