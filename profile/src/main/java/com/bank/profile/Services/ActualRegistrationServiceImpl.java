package com.bank.profile.Services;

import com.bank.profile.Entities.ActualRegistration;
import com.bank.profile.Repositories.ActualRegistrationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class ActualRegistrationServiceImpl implements ActualRegistrationService {
    private final ActualRegistrationRepository actualRegistrationRepository;

    @Autowired
    public ActualRegistrationServiceImpl(ActualRegistrationRepository actualRegistrationRepository) {
        this.actualRegistrationRepository = actualRegistrationRepository;
    }

    @Override
    public ActualRegistration create(ActualRegistration actualRegistration) {
        return actualRegistrationRepository.save(actualRegistration);
    }

    @Override
    public ActualRegistration update(ActualRegistration actualRegistration) {
        return actualRegistrationRepository.save(actualRegistration);
    }

    @Override
    public void delete(Long id) {
        actualRegistrationRepository.deleteById(id);
    }

    @Override
    public ActualRegistration getActualRegistration(Long id) {
        return actualRegistrationRepository.getById(id);
    }

    @Override
    public List<ActualRegistration> getAllActualRegistrations() {
        return actualRegistrationRepository.findAll();
    }
}
