package com.bank.profile.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassportDto {

    private Long id;

    @NotNull(message = "series не может быть null")
    private Integer series;

    @NotNull(message = "number не может быть null")
    private Long number;

    @NotBlank(message = "lastName не может быть пустым")
    @Size(max = 100, message = "lastName не может быть длиннее 100 символов")
    private String lastName;

    @NotBlank(message = "firstName не может быть пустым")
    @Size(max = 100, message = "firstName не может быть длиннее 100 символов")
    private String firstName;

    @Size(max = 100, message = "middleName не может быть длиннее 100 символов")
    private String middleName;

    @NotBlank(message = "gender не может быть пустым")
    @Size(max = 10, message = "gender не может быть длиннее 10 символов")
    private String gender;

    @NotNull(message = "birthDate не может быть null")
    @Past(message = "birthDate должна быть в прошлом")
    private Date birthDate;

    @NotBlank(message = "birthPlace не может быть пустым")
    @Size(max = 100, message = "birthPlace не может быть длиннее 100 символов")
    private String birthPlace;

    @NotBlank(message = "issuedBy не может быть пустым")
    @Size(max = 100, message = "issuedBy не может быть длиннее 100 символов")
    private String issuedBy;

    @NotNull(message = "dateOfIssue не может быть null")
    @Past(message = "dateOfIssue должна быть в прошлом")
    private Date dateOfIssue;

    @NotNull(message = "divisionCode не может быть null")
    private Integer divisionCode;

    @NotNull(message = "expirationDate не может быть null")
    @Past(message = "expirationDate должна быть в прошлом")
    private Date expirationDate;
}
