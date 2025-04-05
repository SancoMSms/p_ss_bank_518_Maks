package com.bank.profile.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PassportDto {
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
}
