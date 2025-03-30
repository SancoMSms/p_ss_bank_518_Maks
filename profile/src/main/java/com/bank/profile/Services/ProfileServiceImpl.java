package com.bank.profile.Services;

import com.bank.profile.Entities.Profile;
import com.bank.profile.Repositories.ProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Profile create(Profile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public Profile update(Profile profile) {
        return profileRepository.save(profile);
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
