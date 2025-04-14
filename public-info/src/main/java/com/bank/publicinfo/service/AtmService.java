package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.AtmDto;

import java.util.List;

public interface AtmService {

    AtmDto addAtm(AtmDto atmDto);

    List<AtmDto> getAllAtm();

    AtmDto getAtmById(Long id);

    AtmDto updateAtm(AtmDto atmDto);

    void deleteAtm(Long id);
}