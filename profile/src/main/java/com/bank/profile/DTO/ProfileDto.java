package com.bank.profile.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    private Long id;
    private Long phoneNumber;
    private String email;
    private String nameOnCard;
    private Long inn;
    private Long snils;
    private PassportDto passport;
    private RegistrationDto registration;
}
