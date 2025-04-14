package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.mapper.BankDetailsMapper;
import com.bank.publicinfo.repository.BankDetailsRepository;
import com.bank.publicinfo.service.BankDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankDetailsServiceImpl implements BankDetailsService {

    private final BankDetailsRepository bankDetailsRepository;
    private final BankDetailsMapper bankDetailsMapper;

    @Override
    @Transactional
    public BankDetailsDto addBankDetails(BankDetailsDto bankDetailsDto) {
        validateBankDetailsDto(bankDetailsDto);

        try {
            BankDetails saved = bankDetailsRepository.save(bankDetailsMapper.toEntity(bankDetailsDto));
            return bankDetailsMapper.toDto(saved);
        } catch (Exception e) {
            log.error("Failed to save BankDetails", e);
            throw new RuntimeException("Error while saving BankDetails", e);
        }
    }

    @Override
    public List<BankDetailsDto> getAllBankDetails() {
        return bankDetailsMapper.toDtoList(bankDetailsRepository.findAll());
    }

    @Override
    public BankDetailsDto getBankDetailsById(Long id) {
        BankDetails bankDetails = bankDetailsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("BankDetails not found with id: " + id));
        return bankDetailsMapper.toDto(bankDetails);
    }

    @Override
    @Transactional
    public BankDetailsDto updateBankDetails(BankDetailsDto bankDetailsDto) {
        validateBankDetailsDto(bankDetailsDto);

        if (bankDetailsDto.getId() == null) {
            throw new IllegalArgumentException("BankDetails ID must not be null when updating.");
        }

        bankDetailsRepository.findById(bankDetailsDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("BankDetails not found with id: " + bankDetailsDto.getId()));

        BankDetails updated = bankDetailsRepository.save(bankDetailsMapper.toEntity(bankDetailsDto));
        return bankDetailsMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteBankDetails(Long id) {
        if (!bankDetailsRepository.existsById(id)) {
            throw new IllegalArgumentException("BankDetails not found with id: " + id);
        }
        bankDetailsRepository.deleteById(id);
    }

    private void validateBankDetailsDto(BankDetailsDto dto) {
        if (dto.getCity() == null ||
                dto.getJointStockCompany() == null ||
                dto.getName() == null) {
            throw new IllegalArgumentException("Fields 'city', 'jointStockCompany', and 'name' must not be null.");
        }
    }

}

