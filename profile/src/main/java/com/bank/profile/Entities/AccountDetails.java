package com.bank.profile.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "account_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;
}
