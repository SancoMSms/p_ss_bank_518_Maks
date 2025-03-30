package com.bank.profile.Entities;

import jakarta.persistence.*;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name = "passport")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer series;
    private Long number;
    private String lastName;
    private String firstName;
    private String middleName;
    private String gender;
    private Date birthDate;
    private String birthPlace;
    private String issuedBy;
    private Date dateOfIssue;
    private Integer divisionCode;
    private Date expirationDate;

    @OneToOne
    @JoinColumn(name = "registration_id")
    private Registration registration;
}

