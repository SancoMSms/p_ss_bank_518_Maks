package com.bank.profile.DTO;

import jakarta.validation.constraints.Email;
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
public class ProfileDto {

    @NotNull(message = "id не может быть null")
    private Long id;

    @NotNull(message = "phoneNumber не может быть null")
    @Pattern(regexp = "^\\d{10}$", message = "phoneNumber должен содержать 10 цифр")
    private Long phoneNumber;

    @NotBlank(message = "email не может быть пустым")
    @Email(message = "email должен быть в правильном формате")
    private String email;

    @NotBlank(message = "nameOnCard не может быть пустым")
    @Size(max = 100, message = "nameOnCard не может быть длиннее 100 символов")
    private String nameOnCard;

    @NotNull(message = "inn не может быть null")
    @Pattern(regexp = "^\\d{10}$", message = "inn должен содержать 10 цифр")
    private Long inn;

    @NotNull(message = "snils не может быть null")
    @Pattern(regexp = "^\\d{11}$", message = "snils должен содержать 11 цифр")
    private Long snils;

    @NotNull(message = "passport не может быть null")
    private PassportDto passport;

    @NotNull(message = "registration не может быть null")
    private RegistrationDto registration;
}
