package com.bank.profile.Services;

import com.bank.profile.DTO.PassportDto;
import com.bank.profile.Entities.Passport;
import com.bank.profile.Mappers.PassportMapper;
import com.bank.profile.Repositories.PassportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassportServiceImpl implements PassportService {
    private final PassportRepository passportRepository;
    private final PassportMapper passportMapper;

    public PassportServiceImpl(PassportRepository passportRepository, PassportMapper passportMapper) {
        this.passportRepository = passportRepository;
        this.passportMapper = passportMapper;
    }

    @Override
    public Passport create(PassportDto passportDto) {
        return passportRepository.save(passportMapper.toEntity(passportDto));
    }

    @Override
    public Passport update(PassportDto passportDto) {
        return passportRepository.save(passportMapper.toEntity(passportDto));
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
