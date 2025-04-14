package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.CertificateDto;

import java.util.List;

public interface CertificateService {

    CertificateDto addCertificate(CertificateDto certificateDto);

    List<CertificateDto> getAllCertificate();

    CertificateDto getCertificateById(Long id);

    CertificateDto updateCertificate(CertificateDto certificateDto);

    void deleteCertificate(Long id);
}