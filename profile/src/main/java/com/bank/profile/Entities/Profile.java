package com.bank.profile.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long phoneNumber;
    private String email;
    private String nameOnCard;
    private Long inn;
    private Long snils;

    @OneToOne
    @JoinColumn(name = "passport_id")
    private Passport passport;

    @OneToOne
    @JoinColumn(name = "actual_registration_id")
    private ActualRegistration actualRegistration;
}
