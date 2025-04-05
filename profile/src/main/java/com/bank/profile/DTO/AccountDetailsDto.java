package com.bank.profile.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetailsDto {
    private Long id;
    private Long accountId;
    private Long profileId;
}
