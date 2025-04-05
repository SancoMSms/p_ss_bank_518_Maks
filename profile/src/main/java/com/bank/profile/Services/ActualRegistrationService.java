package com.bank.profile.Services;

import com.bank.profile.DTO.ActualRegistrationDto;
import com.bank.profile.Entities.ActualRegistration;

import java.util.List;

public interface ActualRegistrationService {
    ActualRegistration create(ActualRegistrationDto actualRegistrationDto);

    ActualRegistration update(ActualRegistrationDto actualRegistrationDto);

    void delete(Long id);

    ActualRegistration getActualRegistration(Long id);

    List<ActualRegistration> getAllActualRegistrations();
}
