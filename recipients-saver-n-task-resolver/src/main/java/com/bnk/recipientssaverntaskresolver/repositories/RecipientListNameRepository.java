package com.bnk.recipientssaverntaskresolver.repositories;

import com.bnk.recipientssaverntaskresolver.entities.recipietns_saver_service.Recipient;
import com.bnk.recipientssaverntaskresolver.entities.recipietns_saver_service.RecipientList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipientListNameRepository extends
        JpaRepository<String, Long> {
//    List<Recipient> findAllRecipientsByName(String recipientListName);
    RecipientList findAllByName(String recipientListName);
}
