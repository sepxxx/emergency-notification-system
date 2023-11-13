package com.bnk.recipientssaverntaskresolver.repositories;

import com.bnk.recipientssaverntaskresolver.entities.recipietns_saver_service.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipientListNameRepository extends
        JpaRepository<String, Long> {
    List<Recipient> findAllByListName(String recipientListName);
}
