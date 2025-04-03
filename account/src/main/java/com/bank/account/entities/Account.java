package com.bank.account.entities;

import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account_details", schema = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "passport_id")
    private Long passportId;

    @Column(name = "account_number", unique = true)
    private Long accountNumber;

    @Column(name = "bank_details_id", unique = true)
    private Long bankDetailsId;

    @Column(name = "money")
    private BigDecimal money;

    @Column(name = "negative_balance")
    private Boolean negativeBalance;

    @Column(name = "profile_id")
    private Long profileId;
}
