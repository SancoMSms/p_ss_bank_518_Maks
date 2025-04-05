package com.bank.profile.Services;

import com.bank.profile.DTO.ProfileDto;
import com.bank.profile.Entities.Profile;
import com.bank.profile.Mappers.ProfileMapper;
import com.bank.profile.Repositories.ProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository, ProfileMapper profileMapper) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }

    @Override
    public Profile create(ProfileDto profileDto) {
        return profileRepository.save(profileMapper.toEntity(profileDto));
    }

    @Override
    public Profile update(ProfileDto profileDto) {
        return profileRepository.save(profileMapper.toEntity(profileDto));
    }

    @Override
    public void delete(Long id) {
        profileRepository.deleteById(id);
    }

    @Override
    public Profile getProfile(Long id) {
        return profileRepository.getById(id);
    }

    @Override
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }
}
