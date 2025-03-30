package com.bank.profile.Services;

import com.bank.profile.Entities.Profile;
import com.bank.profile.Entities.Registration;

import java.util.List;

public interface RegistrationService {
    Registration create (Registration registration);
    Registration update (Registration registration);
    void delete (Long id);
    Registration getRegistration (Long id);
    List<Registration> getAllRegistrations();
}
