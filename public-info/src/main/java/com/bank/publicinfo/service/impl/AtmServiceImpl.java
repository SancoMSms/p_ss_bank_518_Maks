package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.entity.Atm;
import com.bank.publicinfo.mapper.AtmMapper;
import com.bank.publicinfo.repository.AtmRepository;
import com.bank.publicinfo.service.AtmService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AtmServiceImpl implements AtmService {

    private final AtmRepository atmRepository;
    private final AtmMapper atmMapper;

    @Override
    @Transactional
    public AtmDto addAtm(AtmDto atmDto) {
        if (atmDto.getAddress() == null) {
            throw new IllegalArgumentException("Address field cannot be null.");
        }

        try {
            Atm saved = atmRepository.save(atmMapper.toEntity(atmDto));
            return atmMapper.toDto(saved);
        } catch (Exception e) {
            log.error("Failed to save ATM.", e);
            throw new RuntimeException("Unable to save ATM.", e);
        }
    }

    @Override
    public List<AtmDto> getAllAtm() {
        return atmMapper.toDtoList(atmRepository.findAll());
    }

    @Override
    public AtmDto getAtmById(Long id) {
        Atm atm = atmRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ATM not found with id: " + id));
        return atmMapper.toDto(atm);
    }

    @Override
    @Transactional
    public AtmDto updateAtm(AtmDto atmDto) {
        validateAtmDto(atmDto);

        if (atmDto.getId() == null) {
            throw new IllegalArgumentException("ATM ID must not be null when updating.");
        }

        atmRepository.findById(atmDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("ATM not found with id: " + atmDto.getId()));

        Atm updated = atmRepository.save(atmMapper.toEntity(atmDto));
        return atmMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteAtm(Long id) {
        if (!atmRepository.existsById(id)) {
            throw new IllegalArgumentException("ATM not found with id: " + id);
        }

        atmRepository.deleteById(id);
    }

    private void validateAtmDto(AtmDto atmDto) {
        if (atmDto.getAddress() == null) {
            throw new IllegalArgumentException("Address field cannot be null.");
        }
    }
}

