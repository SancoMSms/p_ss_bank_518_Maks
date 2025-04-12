package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.Certificate;
import com.bank.publicinfo.mapper.CertificateMapper;
import com.bank.publicinfo.repository.CertificateRepository;
import com.bank.publicinfo.service.CertificateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;

    @Override
    @Transactional
    public CertificateDto addCertificate(CertificateDto certificateDto) {
        validateCertificateDto(certificateDto);
        try {
            Certificate saved = certificateRepository.save(certificateMapper.toEntity(certificateDto));
            return certificateMapper.toDto(saved);
        } catch (Exception e) {
            log.error("Failed to save certificate", e);
            throw new RuntimeException("Error while saving certificate", e);
        }
    }

    @Override
    public List<CertificateDto> getAllCertificate() {
        return certificateMapper.toDtoList(certificateRepository.findAll());
    }

    @Override
    public CertificateDto getCertificateById(Long id) {
        Certificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Certificate not found with id: " + id));
        return certificateMapper.toDto(certificate);
    }

    @Override
    @Transactional
    public CertificateDto updateCertificate(CertificateDto certificateDto) {
        validateCertificateDto(certificateDto);

        if (certificateDto.getId() == null) {
            throw new IllegalArgumentException("Certificate ID must not be null when updating.");
        }

        certificateRepository.findById(certificateDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Certificate not found with id: " + certificateDto.getId()));

        Certificate updated = certificateRepository.save(certificateMapper.toEntity(certificateDto));
        return certificateMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteCertificate(Long id) {
        if (!certificateRepository.existsById(id)) {
            throw new IllegalArgumentException("Certificate not found with id: " + id);
        }
        certificateRepository.deleteById(id);
    }

    private void validateCertificateDto(CertificateDto dto) {
        if (dto.getPhoto() == null) {
            throw new IllegalArgumentException("Field 'photo' must not be null.");
        }
    }
}


