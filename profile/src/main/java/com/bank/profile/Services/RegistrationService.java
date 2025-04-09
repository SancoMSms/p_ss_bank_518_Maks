package com.bank.profile.Services;

import com.bank.profile.DTO.RegistrationDto;
import com.bank.profile.Entities.Registration;

import java.util.List;

public interface RegistrationService {
    Registration create(RegistrationDto registrationDto);

    Registration update(RegistrationDto registrationDto);

    void delete(Long id);

    Registration getRegistration(Long id);

    List<Registration> getAllRegistrations();
}
