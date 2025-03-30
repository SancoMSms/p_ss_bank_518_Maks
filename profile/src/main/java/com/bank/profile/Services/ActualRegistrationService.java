package com.bank.profile.Services;

import com.bank.profile.Entities.ActualRegistration;

import java.util.List;

public interface ActualRegistrationService {
    ActualRegistration create(ActualRegistration actualRegistration);

    ActualRegistration update(ActualRegistration actualRegistration);

    void delete(Long id);

    ActualRegistration getActualRegistration(Long id);

    List<ActualRegistration> getAllActualRegistrations();
}
