package com.bank.profile.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "actual_registration")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActualRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;
    private String region;
    private String city;
    private String district;
    private String locality;
    private String street;
    private String houseNumber;
    private String houseBlock;
    private String flatNumber;
    private Integer index;
}
