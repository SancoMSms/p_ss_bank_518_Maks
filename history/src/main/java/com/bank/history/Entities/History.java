package com.bank.history.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "history")
@Data
@NoArgsConstructor
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transfer_audit_id", nullable = false)
    private Long transferAuditId;

    @Column(name = "profile_audit_id", nullable = false)
    private Long profileAuditId;

    @Column(name = "account_audit_id", nullable = false)
    private Long accountAuditId;

    @Column(name = "anti_fraud_audit_id", nullable = false)
    private Long antiFraudAuditId;

    @Column(name = "public_bank_info_audit_id", nullable = false)
    private Long publicBankInfoAuditId;

    @Column(name = "authorization_audit_id", nullable = false)
    private Long authorizationAuditId;
}
