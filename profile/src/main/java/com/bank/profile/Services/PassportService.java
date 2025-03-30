package com.bank.profile.Services;

import com.bank.profile.Entities.Passport;

import java.util.List;

public interface PassportService {
    Passport create(Passport Passport);

    Passport update(Passport Passport);

    void delete(Long id);

    Passport getPassport(Long id);

    List<Passport> getAllPassports();
}
