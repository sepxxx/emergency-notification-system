package com.bnk.recipientssaverntaskresolver.repositories;

import com.bnk.recipientssaverntaskresolver.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
