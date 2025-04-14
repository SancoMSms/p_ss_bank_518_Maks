package com.bank.publicinfo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.OffsetTime;

@Data
public class AtmDto {

    private Long id;

    @NotBlank(message = "Address cannot be blank")
    @Size(max = 255, message = "Address must be less than 255 characters")
    private String address;

    private OffsetTime startOfWork;
    private OffsetTime endOfWork;
    private boolean allHours;

    @NotNull(message = "Branch ID must not be null")
    private Long branchId;
}
