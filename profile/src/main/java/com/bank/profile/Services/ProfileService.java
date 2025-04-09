package com.bank.profile.Services;

import com.bank.profile.DTO.ProfileDto;
import com.bank.profile.Entities.Profile;

import java.util.List;

public interface ProfileService {
    Profile create(ProfileDto profileDto);

    Profile update(ProfileDto profileDto);

    void delete(Long id);

    Profile getProfile(Long id);

    List<Profile> getAllProfiles();
}
