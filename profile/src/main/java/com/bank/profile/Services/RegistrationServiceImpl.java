package com.bank.profile.Services;

import com.bank.profile.Entities.Registration;
import com.bank.profile.Repositories.RegistrationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Transactional
@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final RegistrationRepository registrationRepository;

    @Autowired
    public RegistrationServiceImpl(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Override
    public Registration create(Registration registration) {
        return registrationRepository.save(registration);
    }

    @Override
    public Registration update(Registration registration) {
        return registrationRepository.save(registration);
    }

    @Override
    public void delete(Long id) {
        registrationRepository.deleteById(id);
    }

    @Override
    public Registration getRegistration(Long id) {
        return registrationRepository.getById(id);
    }

    @Override
    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }
}
