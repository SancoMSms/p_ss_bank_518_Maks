package com.bank.publicinfo.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BankDetailsDto {

    private Long id;

    @NotNull(message = "BIK must not be null")
    @Digits(integer = 9, fraction = 0, message = "BIK must be up to 9 digits")
    private Long bik;

    @NotNull(message = "INN must not be null")
    @Digits(integer = 12, fraction = 0, message = "INN must be up to 12 digits")
    private Long inn;

    @NotNull(message = "KPP must not be null")
    @Digits(integer = 9, fraction = 0, message = "KPP must be up to 9 digits")
    private Long kpp;

    @NotNull(message = "CorAccount must not be null")
    @Digits(integer = 20, fraction = 0, message = "CorAccount must be up to 20 digits")
    private Long corAccount;

    @NotBlank(message = "City must not be blank")
    private String city;

    @NotBlank(message = "Joint stock company must not be blank")
    private String jointStockCompany;

    @NotBlank(message = "Name must not be blank")
    private String name;
}