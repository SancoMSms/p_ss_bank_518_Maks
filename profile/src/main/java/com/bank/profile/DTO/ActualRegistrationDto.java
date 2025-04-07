package com.bank.profile.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActualRegistrationDto {

    private Long id;

    @NotBlank(message = "country не может быть пустым")
    private String country;

    @NotBlank(message = "region не может быть пустым")
    private String region;

    @NotBlank(message = "city не может быть пустым")
    private String city;

    @NotBlank(message = "district не может быть пустым")
    private String district;

    @NotBlank(message = "locality не может быть пустым")
    private String locality;

    @NotBlank(message = "street не может быть пустым")
    private String street;

    @NotBlank(message = "houseNumber не может быть пустым")
    private String houseNumber;

    private String houseBlock;

    private String flatNumber;

    @NotNull(message = "index не может быть null")
    @Positive(message = "index должен быть положительным числом")
    private Integer index;
}
