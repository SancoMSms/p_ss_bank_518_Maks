package com.bank.account.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AccountDto {

    @NotNull(message = "id не может быть null")
    private Long id;

    @NotNull(message = "passportId не может быть null")
    private Long passportId;

    @NotNull(message = "accountNumber не может быть null")
    private Long accountNumber;

    @NotNull(message = "bankDetailsId не может быть null")
    private Long bankDetailsId;

    @NotNull(message = "money не может быть null")
    private BigDecimal money;

    @NotNull(message = "negativeBalance не может быть null")
    private Boolean negativeBalance;

    @NotNull(message = "profileId не может быть null")
    private Long profileId;
}
