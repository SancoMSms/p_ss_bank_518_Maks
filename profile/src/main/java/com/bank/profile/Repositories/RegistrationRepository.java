package com.bank.profile.Repositories;

import com.bank.profile.Entities.Profile;
import com.bank.profile.Entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    public void deleteById(Long id);
}
