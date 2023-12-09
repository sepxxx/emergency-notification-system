package com.bnk.recipientssaverntaskresolver.repositories;

import com.bnk.miscellaneous.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //readOnly = true
    @Transactional
    Optional<User> findByUsername(String username);
    @Transactional
    Optional<User> findById(Long userId);
}
