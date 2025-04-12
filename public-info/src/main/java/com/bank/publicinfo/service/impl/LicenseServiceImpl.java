package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.License;
import com.bank.publicinfo.mapper.LicenseMapper;
import com.bank.publicinfo.repository.LicenseRepository;
import com.bank.publicinfo.service.LicenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LicenseServiceImpl implements LicenseService {

    private final LicenseRepository licenseRepository;
    private final LicenseMapper licenseMapper;

    @Override
    @Transactional
    public LicenseDto addLicense(LicenseDto licenseDto) {
        validateLicenseDto(licenseDto);
        try {
            License saved = licenseRepository.save(licenseMapper.toEntity(licenseDto));
            return licenseMapper.toDto(saved);
        } catch (Exception e) {
            log.error("Failed to save license", e);
            throw new RuntimeException("Error while saving license", e);
        }
    }

    @Override
    public List<LicenseDto> getAllLicense() {
        return licenseMapper.toDtoList(licenseRepository.findAll());
    }

    @Override
    public LicenseDto getLicenseById(Long id) {
        License license = licenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("License not found with id: " + id));
        return licenseMapper.toDto(license);
    }

    @Override
    @Transactional
    public LicenseDto updateLicense(LicenseDto licenseDto) {
        validateLicenseDto(licenseDto);

        if (licenseDto.getId() == null) {
            throw new IllegalArgumentException("License ID must not be null when updating.");
        }

        licenseRepository.findById(licenseDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("License not found with id: " + licenseDto.getId()));

        License updated = licenseRepository.save(licenseMapper.toEntity(licenseDto));
        return licenseMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteLicense(Long id) {
        if (!licenseRepository.existsById(id)) {
            throw new IllegalArgumentException("License not found with id: " + id);
        }
        licenseRepository.deleteById(id);
    }

    private void validateLicenseDto(LicenseDto dto) {
        if (dto.getPhoto() == null) {
            throw new IllegalArgumentException("Field 'photo' must not be null.");
        }
    }
}
