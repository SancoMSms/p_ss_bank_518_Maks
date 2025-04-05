package com.bank.profile.Services;

import com.bank.profile.DTO.RegistrationDto;
import com.bank.profile.Entities.Registration;
import com.bank.profile.Mappers.RegistrationMappers;
import com.bank.profile.Repositories.RegistrationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final RegistrationMappers registrationMappers;

    @Autowired
    public RegistrationServiceImpl(RegistrationRepository registrationRepository, RegistrationMappers registrationMappers) {
        this.registrationRepository = registrationRepository;
        this.registrationMappers = registrationMappers;
    }

    @Override
    public Registration create(RegistrationDto registrationDto) {
        return registrationRepository.save(registrationMappers.toEntity(registrationDto));
    }

    @Override
    public Registration update(RegistrationDto registrationDto) {
        return registrationRepository.save(registrationMappers.toEntity(registrationDto));
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
