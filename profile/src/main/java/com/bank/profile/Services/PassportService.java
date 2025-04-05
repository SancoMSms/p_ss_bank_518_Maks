package com.bank.profile.Services;

import com.bank.profile.DTO.PassportDto;
import com.bank.profile.Entities.Passport;

import java.util.List;

public interface PassportService {
    Passport create(PassportDto passportDto);

    Passport update(PassportDto passportDto);

    void delete(Long id);

    Passport getPassport(Long id);

    List<Passport> getAllPassports();
}
