package com.bank.profile.Services;

import com.bank.profile.DTO.ActualRegistrationDto;
import com.bank.profile.Entities.ActualRegistration;
import com.bank.profile.Mappers.ActualRegistrationMapper;
import com.bank.profile.Repositories.ActualRegistrationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class ActualRegistrationServiceImpl implements ActualRegistrationService {
    private final ActualRegistrationRepository actualRegistrationRepository;
    private final ActualRegistrationMapper actualRegistrationMapper;

    @Autowired
    public ActualRegistrationServiceImpl(ActualRegistrationRepository actualRegistrationRepository, ActualRegistrationMapper actualRegistrationMapper) {
        this.actualRegistrationRepository = actualRegistrationRepository;
        this.actualRegistrationMapper = actualRegistrationMapper;
    }

    @Override
    public ActualRegistration create(ActualRegistrationDto actualRegistrationDto) {
        return actualRegistrationRepository.save(actualRegistrationMapper.toEntity(actualRegistrationDto));
    }

    @Override
    public ActualRegistration update(ActualRegistrationDto actualRegistrationDto) {
        return actualRegistrationRepository.save(actualRegistrationMapper.toEntity(actualRegistrationDto));
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
