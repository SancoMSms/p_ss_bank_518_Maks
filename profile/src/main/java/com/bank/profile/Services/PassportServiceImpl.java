package com.bank.profile.Services;

import com.bank.profile.Entities.Passport;
import com.bank.profile.Repositories.PassportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassportServiceImpl implements PassportService {
    private final PassportRepository passportRepository;
    @Autowired
    public PassportServiceImpl(PassportRepository passportRepository) {
        this.passportRepository = passportRepository;
    }

    @Override
    public Passport create(Passport Passport) {
        return passportRepository.save(Passport);
    }

    @Override
    public Passport update(Passport Passport) {
        return passportRepository.save(Passport);
    }

    @Override
    public void delete(Long id) {
        passportRepository.deleteById(id);
    }

    @Override
    public Passport getPassport(Long id) {
        return passportRepository.getById(id);
    }

    @Override
    public List<Passport> getAllPassports() {
        return passportRepository.findAll();
    }
}
