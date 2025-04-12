package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.Branch;
import com.bank.publicinfo.mapper.BranchMapper;
import com.bank.publicinfo.repository.BranchRepository;
import com.bank.publicinfo.service.BranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;

    @Override
    @Transactional
    public BranchDto addBranch(BranchDto branchDto) {
        validateBranchDto(branchDto);
        try {
            Branch saved = branchRepository.save(branchMapper.toEntity(branchDto));
            return branchMapper.toDto(saved);
        } catch (Exception e) {
            log.error("Failed to save Branch", e);
            throw new RuntimeException("Error while saving Branch", e);
        }
    }

    @Override
    public List<BranchDto> getAllBranch() {
        return branchMapper.toDtoList(branchRepository.findAll());
    }

    @Override
    public BranchDto getBranchById(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Branch not found with id: " + id));
        return branchMapper.toDto(branch);
    }

    @Override
    @Transactional
    public BranchDto updateBranch(BranchDto branchDto) {
        validateBranchDto(branchDto);

        if (branchDto.getId() == null) {
            throw new IllegalArgumentException("Branch ID must not be null when updating.");
        }

        branchRepository.findById(branchDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Branch not found with id: " + branchDto.getId()));

        Branch updated = branchRepository.save(branchMapper.toEntity(branchDto));
        return branchMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteBranch(Long id) {
        if (!branchRepository.existsById(id)) {
            throw new IllegalArgumentException("Branch not found with id: " + id);
        }
        branchRepository.deleteById(id);
    }

    private void validateBranchDto(BranchDto dto) {
        if (dto.getAddress() == null || dto.getCity() == null) {
            throw new IllegalArgumentException("Fields 'address' and 'city' must not be null.");
        }
    }
}
