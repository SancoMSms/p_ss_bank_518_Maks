package com.bank.publicinfo.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bank_details", schema = "public_bank_information")
public class BankDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bik", unique = true)
    private Long bik;

    @Column(name = "inn", unique = true)
    private Long inn;

    @Column(name = "kpp", unique = true)
    private Long kpp;

    @Column(name = "cor_account", unique = true)
    private Long corAccount;

    @Column(name = "city")
    private String city;

    @Column(name = "joint_stock_company")
    private String jointStockCompany;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "bankDetails", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<License> licenseList;

    @OneToMany(mappedBy = "bankDetails",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Certificate> certificateList;
}