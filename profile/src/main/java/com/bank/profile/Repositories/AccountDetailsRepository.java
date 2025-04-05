package com.bank.profile.Repositories;

import com.bank.profile.Entities.AccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountDetails, Long> {
    public void deleteById(Long id);
}
