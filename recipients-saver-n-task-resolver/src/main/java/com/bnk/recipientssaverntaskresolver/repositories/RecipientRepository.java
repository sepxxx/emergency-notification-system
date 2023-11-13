package com.bnk.recipientssaverntaskresolver.repositories;

import com.bnk.recipientssaverntaskresolver.entities.recipietns_saver_service.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Long> {
}
