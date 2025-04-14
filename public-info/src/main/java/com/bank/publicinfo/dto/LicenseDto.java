package com.bank.publicinfo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LicenseDto {

    private Long id;

    @NotNull(message = "Photo must not be null")
    private byte[] photo;

    @NotNull(message = "BankDetailsId must not be null")
    private Long bankDetailsId;
}
