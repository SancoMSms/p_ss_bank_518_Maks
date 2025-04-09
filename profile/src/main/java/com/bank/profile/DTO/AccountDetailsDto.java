package com.bank.profile.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetailsDto {

    private Long id;

    @NotNull(message = "accountId не может быть null")
    private Long accountId;

    @NotNull(message = "profileId не может быть null")
    private Long profileId;
}
