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
    private  long  transfer_audit_id;
    private  long  profile_audit_id;
    private  long  account_audit_id;
    private  long  anti_fraud_audit_id;
    private  long  public_bank_info_audit_id;
    private  long  authorization_audit_id;
}
