package com.bank.profile.Services;

import com.bank.profile.Entities.Profile;

import java.util.List;

public interface ProfileService {
    Profile create (Profile profile);
    Profile update (Profile profile);
    void delete (Long id);
    Profile getProfile (Long id);
    List<Profile> getAllProfiles ();
}
