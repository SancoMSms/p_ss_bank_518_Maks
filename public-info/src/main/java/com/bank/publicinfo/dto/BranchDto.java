package com.bank.publicinfo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.OffsetTime;

@Data
public class BranchDto {

    private Long id;

    @NotBlank(message = "Address must not be blank")
    private String address;

    @Min(value = 1000000, message = "Phone number must be at least 7 digits")
    private int phoneNumber;

    @NotBlank(message = "City must not be blank")
    private String city;

    private OffsetTime startOfWork;
    private OffsetTime endOfWork;
}