package com.bank.authorization.Repositories;


import com.bank.authorization.Entities.Role;
import com.bank.authorization.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByProfileId( Long profileId);



}
