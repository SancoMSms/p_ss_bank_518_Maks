package com.bank.profile.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {

    @NotNull(message = "id не может быть null")
    private Long id;

    @NotBlank(message = "country не может быть пустым")
    @Size(max = 100, message = "country не может быть длиннее 100 символов")
    private String country;

    @NotBlank(message = "region не может быть пустым")
    @Size(max = 100, message = "region не может быть длиннее 100 символов")
    private String region;

    @NotBlank(message = "city не может быть пустым")
    @Size(max = 100, message = "city не может быть длиннее 100 символов")
    private String city;

    @NotBlank(message = "district не может быть пустым")
    @Size(max = 100, message = "district не может быть длиннее 100 символов")
    private String district;

    @NotBlank(message = "locality не может быть пустым")
    @Size(max = 100, message = "locality не может быть длиннее 100 символов")
    private String locality;

    @NotBlank(message = "street не может быть пустым")
    @Size(max = 100, message = "street не может быть длиннее 100 символов")
    private String street;

    @NotBlank(message = "houseNumber не может быть пустым")
    @Pattern(regexp = "^\\d{1,5}$", message = "houseNumber должен быть числовым и содержать от 1 до 5 цифр")
    private String houseNumber;

    @Size(max = 10, message = "houseBlock не может быть длиннее 10 символов")
    private String houseBlock;

    @Size(max = 10, message = "flatNumber не может быть длиннее 10 символов")
    private String flatNumber;

    @NotNull(message = "index не может быть null")
    @Pattern(regexp = "^\\d{6}$", message = "index должен содержать 6 цифр")
    private Integer index;
}
